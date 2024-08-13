package com.food_delivery.identity.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponse {
    Integer id;
    String email;
    String role;
    Boolean isEmailVerified;
    String fullName;
    String imageUrl;
    String phoneNumber;
    List<AddressResponse> addresses;
    String accessToken;
    String refreshToken;
}
