package com.food_delivery.identity.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationEmailRequest {
    @NotNull(message = "Otp is required")
    private Integer otp;

    @NotNull(message = "User ID is required")
    private Integer userId;
}
