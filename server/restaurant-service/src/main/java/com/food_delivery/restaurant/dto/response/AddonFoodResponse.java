package com.food_delivery.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddonFoodResponse {
    private String id;
    private String restaurantId;
    private String name;
    private String imageUrl;
    private Double price;
}
