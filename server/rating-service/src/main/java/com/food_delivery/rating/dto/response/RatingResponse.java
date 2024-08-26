package com.food_delivery.rating.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {
    private String id;
    private String restaurantId;
    private String foodId;
    private Integer userId;
    private String username;
    private String avatarUrl;
    private int ratingStar;
    private String comment;
    private String createdAt;
}
