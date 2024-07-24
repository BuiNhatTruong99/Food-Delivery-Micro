package com.food_delivery.identity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    Integer id;
    String fullName;
    String email;
    String role;
    Boolean isEmailVerified;
    String accessToken;
}
