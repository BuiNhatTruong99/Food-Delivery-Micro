package com.food_delivery.identity.controller;

import com.food_delivery.identity.dto.request.authentication.*;
import com.food_delivery.identity.dto.response.ApiResponse;
import com.food_delivery.identity.dto.response.IntrospectTokenResponse;
import com.food_delivery.identity.dto.response.TokenRefreshResponse;
import com.food_delivery.identity.dto.response.UserResponse;
import com.food_delivery.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<UserResponse>> signUp(
            @Valid
            @RequestBody UserSignUpRequest userSignUpRequest
    ) {
        var data = userService.signUp(userSignUpRequest);
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder().data(data).build());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<UserResponse>> signIn(
            @Valid
            @RequestBody UserSignInRequest userSignInRequest
    ) {
        var data = userService.signIn(userSignInRequest);
        return ResponseEntity.ok(ApiResponse
                .<UserResponse>builder().data(data).build());
    }

    @PostMapping("/google-sign")
    public ResponseEntity<ApiResponse<UserResponse>> googleSignIn(
            @Valid
            @RequestBody GoogleSignInRequest googleSignInRequest
    ) {
        var data = userService.googleSignIn(googleSignInRequest);
        return ResponseEntity.ok(ApiResponse
                .<UserResponse>builder().data(data).build());
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectTokenResponse>> introspectToken(
            @RequestBody IntrospectTokenRequest introspectTokenRequest
    ) {
        var data = userService.introspectToken(introspectTokenRequest);
        return ResponseEntity.ok(ApiResponse
                .<IntrospectTokenResponse>builder().data(data).build());
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<?>> sendOtp(
            @Valid
            @RequestBody SendOtpRequest sendOtpRequest
    ) {
        userService.sendOtp(sendOtpRequest);
        return ResponseEntity.ok(ApiResponse.<Object>builder().build());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<?>> resetPassword(
            @Valid
            @RequestBody SendOtpRequest sendOtpRequest
    ) {
        userService.resetPassword(sendOtpRequest);
        return ResponseEntity.ok(ApiResponse.<Object>builder().build());
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponse<?>> changePassword(
            @Valid
            @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(ApiResponse.<Object>builder().build());
    }


    @PostMapping("/new-token")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> sendOtp(
            @Valid
            @RequestBody TokenRefreshRequest tokenRefreshRequest
    ) {
        var data = userService.tokenRefresh(tokenRefreshRequest);
        return ResponseEntity.ok(ApiResponse.<TokenRefreshResponse>builder().data(data).build());
    }
}
