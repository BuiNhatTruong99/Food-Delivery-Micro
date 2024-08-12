package com.food_delivery.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleSignInRequest {
    @NotBlank(message = "Token is required")
    private String token;

    @NotBlank(message = "Provider is required")
    private String provider;
}
