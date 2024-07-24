package com.food_delivery.identity.service;

import com.food_delivery.identity.dto.request.GoogleSignInRequest;
import com.food_delivery.identity.dto.request.UserSignInRequest;
import com.food_delivery.identity.dto.request.UserSignUpRequest;
import com.food_delivery.identity.dto.response.UserResponse;
import com.food_delivery.identity.entity.AuthProvider;
import com.food_delivery.identity.entity.User;
import com.food_delivery.identity.exception.DuplicateResourceException;
import com.food_delivery.identity.exception.ErrorCode;
import com.food_delivery.identity.mapper.UserMapper;
import com.food_delivery.identity.repository.UserRepository;
import com.food_delivery.identity.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserResponse signUp(UserSignUpRequest userSignUpRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userSignUpRequest.getEmail());
        if (userOptional.isPresent()) {
            throw new DuplicateResourceException(ErrorCode.ERR_USER_DUPLICATE);
        }
        var user = userMapper.toUser(userSignUpRequest);
        user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
        userRepository.save(user);
        var accessToken = jwtService.generateJwtToken(user);
        var userResponse = userMapper.toUserResponse(user);
        userResponse.setAccessToken(accessToken);
        return userResponse;
    }

    @Override
    public UserResponse signIn(UserSignInRequest userSignInRequest) {
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userSignInRequest.getEmail(),
                        userSignInRequest.getPassword()
                )
        );
        User user = (User) authentication.getPrincipal();
        var accessToken = jwtService.generateJwtToken(user);
        var userResponse = userMapper.toUserResponse(user);
        userResponse.setAccessToken(accessToken);
        return userResponse;
    }

    @Override
    public UserResponse googleSignIn(GoogleSignInRequest googleSignInRequest) {
        Optional<User> userOptional = userRepository.findByEmail(googleSignInRequest.getEmail());
        User user;
        if (userOptional.isEmpty()) {
            AuthProvider provider = AuthProvider.valueOf(googleSignInRequest.getAuthProvider().toUpperCase());
            googleSignInRequest.setAuthProvider(String.valueOf(provider));
            User newUser = userMapper.toUser(googleSignInRequest);
            user = userRepository.save(newUser);
        } else {
            user = userOptional.get();
        }
        var accessToken = jwtService.generateJwtToken(user);
        var userResponse = userMapper.toUserResponse(user);
        userResponse.setAccessToken(accessToken);
        return userResponse;
    }
}
