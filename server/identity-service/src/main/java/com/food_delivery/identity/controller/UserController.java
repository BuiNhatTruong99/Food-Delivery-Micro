package com.food_delivery.identity.controller;

import com.food_delivery.identity.dto.request.user.VerificationEmailRequest;
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

    @PostMapping("/verification-email")
    public ResponseEntity<ApiResponse<UserResponse>> verifyEmail(
            @Valid
            @RequestBody VerificationEmailRequest verificationEmailRequest
    ) {
        var user = userService.verificationEmail(verificationEmailRequest);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder().data(user).build());
    }

}
