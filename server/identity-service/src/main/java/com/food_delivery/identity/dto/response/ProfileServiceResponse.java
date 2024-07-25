package com.food_delivery.identity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileServiceResponse {
    Integer id;
    Integer userId;
    String fullName;
    String email;
    String phoneNumber;
    String imageUrl;
}
