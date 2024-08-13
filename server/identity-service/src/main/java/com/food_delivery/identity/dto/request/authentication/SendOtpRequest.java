package com.food_delivery.identity.dto.request.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendOtpRequest {
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;
}
