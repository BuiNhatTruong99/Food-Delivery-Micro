package com.food_delivery.identity.service;

import com.food_delivery.identity.dto.request.authentication.*;
import com.food_delivery.identity.dto.request.user.VerificationEmailRequest;
import com.food_delivery.identity.dto.response.IntrospectTokenResponse;
import com.food_delivery.identity.dto.response.TokenRefreshResponse;
import com.food_delivery.identity.dto.response.UserResponse;
import com.food_delivery.identity.entity.AuthProvider;
import com.food_delivery.identity.entity.User;
import com.food_delivery.identity.entity.VerifyOtp;
import com.food_delivery.identity.exception.AppException;
import com.food_delivery.identity.exception.DuplicateResourceException;
import com.food_delivery.identity.exception.ErrorCode;
import com.food_delivery.identity.exception.ResourceNotFoundException;
import com.food_delivery.identity.mapper.TokenMapper;
import com.food_delivery.identity.mapper.UserMapper;
import com.food_delivery.identity.repository.UserRepository;
import com.food_delivery.identity.repository.VerifyOtpRepository;
import com.food_delivery.identity.security.CustomJwtDecoder;
import com.food_delivery.identity.security.JwtService;
import com.food_delivery.kafka.NotificationOtp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerifyOtpRepository verifyOtpRepository;
    private final JwtService jwtService;
    private final CustomJwtDecoder jwtDecoder;
    private final UserMapper userMapper;
    private final TokenMapper tokenMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public UserResponse getUser(Integer id) {
        var user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_USER_NOT_FOUND));
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .build();
    }

    @Transactional
    @Override
    public UserResponse signUp(UserSignUpRequest userSignUpRequest) {
        log.info("SIGN-UP: Start process for user {}",
                userSignUpRequest.getEmail());
        Optional<User> userOptional = userRepository.findByEmail(userSignUpRequest.getEmail());
        if (userOptional.isPresent()) {
            log.warn("SIGN-UP: User with email {} already exists", userSignUpRequest.getEmail());
            throw new DuplicateResourceException(ErrorCode.ERR_USER_DUPLICATE);
        }
        var user = userMapper.toUser(userSignUpRequest);
        user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
        user.setAuthProvider(AuthProvider.LOCAL);
        userRepository.save(user);

        // Publish otp to kafka
        Integer otp = otpGenerator();
        VerifyOtp verifyOtp = VerifyOtp.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .user(user)
                .build();
        verifyOtpRepository.save(verifyOtp);

        log.info("SIGN-UP: Publish otp to kafka");
        NotificationOtp notificationOtp = NotificationOtp.builder()
                .recipient(user.getEmail())
                .subject("Welcome to Food Delivery")
                .body(String.valueOf(otp))
                .build();
        kafkaTemplate.send("notification-otp", notificationOtp);

        var userResponse = doGenerateToken(user);
        log.info("SIGN-UP: process for user {} end", userSignUpRequest.getEmail());
        return userResponse;
    }

    @Override
    public UserResponse signIn(UserSignInRequest userSignInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userSignInRequest.getEmail(),
                        userSignInRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(userSignInRequest.getEmail()).orElseThrow();
        log.info("SIGN-IN: Get user from authenticationManager: {}", user.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var userResponse = doGenerateToken(user);
        log.info("SIGN-IN: user {}", userSignInRequest.getEmail());
        return userResponse;
    }

    @Override
    public UserResponse googleSignIn(GoogleSignInRequest googleSignInRequest) {
        Jwt decodeToken = jwtDecoder.decode(googleSignInRequest.getToken());
        String decodeEmail = decodeToken.getClaims().get("email").toString();
        Optional<User> userOptional =
                userRepository.findByEmail(decodeEmail);

        User user;
        if (userOptional.isEmpty()) {
            log.warn("GOOGLE: Start process creating new user {}", decodeEmail);
            AuthProvider provider =
                    AuthProvider.valueOf(googleSignInRequest.getProvider().toUpperCase());
            log.info("GOOGLE: Provider is {}", provider);
            User newUser = User.builder()
                    .email(decodeEmail)
                    .authProvider(provider)
                    .isEmailVerified(decodeToken.getClaims().get(
                            "email_verified").toString().equals("true"))
                    .fullName(decodeToken.getClaims().get("name").toString())
                    .imageUrl(decodeToken.getClaims().get("picture").toString())
                    .build();

            user = userRepository.save(newUser);
            log.info("GOOGLE: End process creating new user {}", user.getId());
        } else {
            user = userOptional.get();
            log.info("GOOGLE: User with id {} found", user.getId());
        }
        return doGenerateToken(user);
    }

    @Override
    public IntrospectTokenResponse introspectToken(IntrospectTokenRequest introspectTokenRequest) {
        boolean isValid = true;
        try {
            verifyToken(introspectTokenRequest.getToken());
        } catch (AppException e) {
            isValid = false;
        }
        return IntrospectTokenResponse.builder()
                .isValid(isValid)
                .build();
    }

    @Override
    public Boolean verifyToken(String token) {
        String userEmail = jwtService.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        return jwtService.isValidJwtToken(token, userDetails);
    }

    @Transactional
    @Override
    public UserResponse verificationEmail(VerificationEmailRequest verificationRequest) {
        log.info("VERIFICATION-EMAIL: Start for user {}",
                verificationRequest.getUserId());
        var otp = verifyOtpRepository
                .findByOtpAndUserId(
                        verificationRequest.getOtp(),
                        verificationRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_OTP_NOT_FOUND));
        if (otp.getExpirationTime().before(new Date())) {
            log.warn("VERIFICATION-EMAIL: OTP expired for user {}", verificationRequest.getUserId());
            throw new AppException(ErrorCode.ERR_OTP_EXPIRED);
        }
        var user = userRepository
                .findById(verificationRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_USER_NOT_FOUND));
        user.setIsEmailVerified(true);
        userRepository.save(user);
        log.info("VERIFICATION-EMAIL: End for user {}", verificationRequest.getUserId());
        verifyOtpRepository.deleteByUserId(user.getId());

        return userMapper.toUserResponse(user);
    }

    @Override
    public void sendOtp(SendOtpRequest sendOtpRequest) {
        handleSendNotificationProcess(
                sendOtpRequest.getEmail(),
                "notification-otp");
    }


    @Override
    public void resetPassword(SendOtpRequest request) {
        var user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_USER_NOT_FOUND));
        var provider = user.getAuthProvider();
        if (!provider.equals(AuthProvider.LOCAL)) {
            log.warn("RESET-PASSWORD: Can't reset because provider is {}", provider);
            throw new AppException(ErrorCode.ERR_USER_PROVIDER_NOT_SUPPORTED);
        }
        handleSendNotificationProcess(
                request.getEmail(),
                "notification-reset-password");
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        log.info("CHANGE-PASSWORD: Start for user {}", changePasswordRequest.getEmail());
        var user = userRepository
                .findByEmail(changePasswordRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_USER_NOT_FOUND));
        var otp = verifyOtpRepository
                .findByOtpAndUserId(changePasswordRequest.getOtp(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_OTP_NOT_FOUND));
        if (otp.getExpirationTime().before(new Date())) {
            log.warn("CHANGE-PASSWORD: OTP expired for user {}", user.getId());
            throw new AppException(ErrorCode.ERR_OTP_EXPIRED);
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        userRepository.save(user);
        log.info("CHANGE-PASSWORD: Remove otp for user {}", user.getId());
        verifyOtpRepository.deleteByUserId(user.getId());
        log.info("CHANGE-PASSWORD: End for user {}", user.getId());
    }

    @Override
    public TokenRefreshResponse tokenRefresh(TokenRefreshRequest tokenRefreshRequest) {
        boolean isValid = verifyToken(tokenRefreshRequest.getRefreshToken());
        if (!isValid) {
            log.warn("TOKEN-REFRESH: Invalid refresh token");
            throw new AppException(ErrorCode.ERR_TOKEN_INVALID);
        }
        String email = jwtService.extractUsername(tokenRefreshRequest.getRefreshToken());
        var user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_USER_NOT_FOUND));
        var userResponse = doGenerateToken(user);

        var tokenRefreshResponse = tokenMapper.toTokenRefreshResponse(userResponse);
        tokenRefreshResponse.setRefreshToken(tokenRefreshRequest.getRefreshToken());
        return tokenRefreshResponse;
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(1000, 9999);
    }

    private UserResponse doGenerateToken(User user) {
        String role = user.getRole().name();
        Map<String, Object> claims = new HashMap<>();
        log.info("GENERATE-TOKEN: Put role in claims for user {}", user.getId());
        claims.put("role", role);
        claims.put("id", user.getId().toString());
        var accessToken = jwtService.generateJwtToken(claims, user);
        var refreshToken = jwtService.generateJwtToken(user);
        log.info("GENERATE-TOKEN: SET tokens in response");
        var userResponse = userMapper.toUserResponse(user);
        userResponse.setAccessToken(accessToken);
        userResponse.setRefreshToken(refreshToken);
        return userResponse;
    }

    private void handleSendNotificationProcess(String email, String topic) {
        var user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_USER_NOT_FOUND));

        var otp = otpGenerator();
        var verifyOtp = VerifyOtp.builder()
                .otp(otp)
                .user(user)
                .expirationTime(new Date(System.currentTimeMillis() + 900000)) // 15 minutes
                .build();
        log.info("SEND-NOTIFICATION: Save otp for user {}", user.getId());
        verifyOtpRepository.save(verifyOtp);

        var notificationOtp = NotificationOtp.builder()
                .recipient(user.getEmail())
                .subject("Welcome to Food Delivery")
                .body(String.valueOf(otp))
                .build();
        log.info("SEND-NOTIFICATION: Send data to kafka");
        kafkaTemplate.send(topic, notificationOtp);
    }


}
