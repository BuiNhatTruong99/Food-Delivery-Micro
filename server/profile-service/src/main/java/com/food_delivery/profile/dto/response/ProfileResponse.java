package com.food_delivery.profile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    Integer id;
    Integer userId;
    String fullName;
    String email;
    String phoneNumber;
    String imageUrl;
}
