package com.food_delivery.identity.service;

import com.food_delivery.identity.client.ProfileServiceClient;
import com.food_delivery.identity.dto.request.GoogleSignInRequest;
import com.food_delivery.identity.dto.request.IntrospectTokenRequest;
import com.food_delivery.identity.dto.request.UserSignInRequest;
import com.food_delivery.identity.dto.request.UserSignUpRequest;
import com.food_delivery.identity.dto.response.IntrospectTokenResponse;
import com.food_delivery.identity.dto.response.UserResponse;
import com.food_delivery.identity.entity.AuthProvider;
import com.food_delivery.identity.entity.User;
import com.food_delivery.identity.entity.VerifyOtp;
import com.food_delivery.identity.exception.AppException;
import com.food_delivery.identity.exception.DuplicateResourceException;
import com.food_delivery.identity.exception.ErrorCode;
import com.food_delivery.kafka.NotificationOtp;
import com.food_delivery.identity.mapper.ProfileServiceMapper;
import com.food_delivery.identity.mapper.UserMapper;
import com.food_delivery.identity.repository.UserRepository;
import com.food_delivery.identity.repository.VerifyOtpRepository;
import com.food_delivery.identity.security.JwtService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerifyOtpRepository verifyOtpRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ProfileServiceMapper profileServiceMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final ProfileServiceClient profileServiceClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    @Override
    public UserResponse signUp(UserSignUpRequest userSignUpRequest) {
        log.info("Sign up process for user {} start", userSignUpRequest.getEmail());
        Optional<User> userOptional = userRepository.findByEmail(userSignUpRequest.getEmail());
        if (userOptional.isPresent()) {
            log.warn("User with email {} already exists", userSignUpRequest.getEmail());
            throw new DuplicateResourceException(ErrorCode.ERR_USER_DUPLICATE);
        }
        var user = userMapper.toUser(userSignUpRequest);
        user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
        user.setAuthProvider(AuthProvider.LOCAL);
        userRepository.save(user);

        // Call profile service to create profile
        log.info("Calling profile service");
        var profileRequest = profileServiceMapper.toProfileCreateRequest(userSignUpRequest);
        profileRequest.setUserId(user.getId());
        profileServiceClient.createProfile(profileRequest);

        // Publish otp to kafka
        Integer otp = otpGenerator();
        VerifyOtp verifyOtp = VerifyOtp.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .user(user)
                .build();
        verifyOtpRepository.save(verifyOtp);

        log.info("Publish otp to kafka");
        NotificationOtp notificationOtp = NotificationOtp.builder()
                .recipient(user.getEmail())
                .subject("Welcome to Food Delivery")
                .body(String.valueOf(otp))
                .build();
        kafkaTemplate.send("notification-otp", notificationOtp);

        var accessToken = jwtService.generateJwtToken(putClaimRole(user), user);
        var userResponse = userMapper.toUserResponse(user);
        userResponse.setAccessToken(accessToken);
        log.info("Sign up process for user {} end", userSignUpRequest.getEmail());
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
        log.info("Get user from authenticationManager: {}", user);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var accessToken = jwtService.generateJwtToken(putClaimRole(user), user);
        var userResponse = userMapper.toUserResponse(user);
        userResponse.setAccessToken(accessToken);
        log.info("Signed in user {}", userSignInRequest.getEmail());
        return userResponse;
    }

    private Map<String, Object> putClaimRole(User user) {
        String role = user.getRole().name();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return claims;
    }

    @Override
    public UserResponse googleSignIn(GoogleSignInRequest googleSignInRequest) {
        Optional<User> userOptional = userRepository.findByEmail(googleSignInRequest.getEmail());
        User user;
        if (userOptional.isEmpty()) {
            log.warn("User with email {} not found, creating new user", googleSignInRequest.getEmail());
            AuthProvider provider = AuthProvider.valueOf(googleSignInRequest.getAuthProvider().toUpperCase());
            googleSignInRequest.setAuthProvider(String.valueOf(provider));
            User newUser = userMapper.toUser(googleSignInRequest);
            user = userRepository.save(newUser);
            log.info("User with id {} created", user.getId());

            // Call profile service to create profile
            var profileRequest = profileServiceMapper.toProfileCreateRequest(googleSignInRequest);
            profileRequest.setUserId(user.getId());
            profileServiceClient.createProfile(profileRequest);
            log.info("Profile created for user {}", user.getId());

        } else {
            user = userOptional.get();
            log.info("GOOGLE: User with id {} found", user.getId());
        }
        var accessToken = jwtService.generateJwtToken(putClaimRole(user), user);
        var userResponse = userMapper.toUserResponse(user);
        userResponse.setAccessToken(accessToken);
        return userResponse;
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

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(1000, 9999);
    }
}
