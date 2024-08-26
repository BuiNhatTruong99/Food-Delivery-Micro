package com.food_delivery.rating.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingCreateRequest {
    private String restaurantId;
    private String foodId;
    private int ratingStar;
    private String comment;
}
