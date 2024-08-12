package com.food_delivery.identity.service;

import com.food_delivery.identity.dto.request.*;
import com.food_delivery.identity.dto.response.IntrospectTokenResponse;
import com.food_delivery.identity.dto.response.TokenRefreshResponse;
import com.food_delivery.identity.dto.response.UserResponse;


public interface UserService {
    UserResponse signUp(UserSignUpRequest userSignUpRequest);

    UserResponse signIn(UserSignInRequest userSignInRequest);

    UserResponse googleSignIn(GoogleSignInRequest googleSignInRequest);

    IntrospectTokenResponse introspectToken(IntrospectTokenRequest introspectTokenRequest);

    Boolean verifyToken(String token);

    UserResponse verificationEmail(VerificationEmailRequest verificationRequest);

    void sendOtp(SendOtpRequest sendOtpRequest);

    void resetPassword(SendOtpRequest request);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    TokenRefreshResponse tokenRefresh(TokenRefreshRequest tokenRefreshRequest);
}
