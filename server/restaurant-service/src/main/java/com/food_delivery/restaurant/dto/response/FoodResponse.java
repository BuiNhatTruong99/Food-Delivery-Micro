package com.food_delivery.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodResponse {
    private String id;
    private String restaurantId;
    private String name;
    private String imageUrl;
    private String description;
    private Double price;
    private Double averageStars;
    private Integer totalReviews;
    private String category;
    private List<AddonFoodResponse> addonFoods;
}
