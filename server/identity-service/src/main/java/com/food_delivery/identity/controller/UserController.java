package com.food_delivery.identity.controller;

import com.food_delivery.identity.dto.request.GoogleSignInRequest;
import com.food_delivery.identity.dto.request.UserSignInRequest;
import com.food_delivery.identity.dto.request.UserSignUpRequest;
import com.food_delivery.identity.dto.response.ApiResponse;
import com.food_delivery.identity.dto.response.UserResponse;
import com.food_delivery.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

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
}
