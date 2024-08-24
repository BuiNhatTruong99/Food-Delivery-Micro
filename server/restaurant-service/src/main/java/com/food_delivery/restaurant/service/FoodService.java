package com.food_delivery.restaurant.service;

import com.food_delivery.restaurant.dto.request.food.AddonFoodCreateRequest;
import com.food_delivery.restaurant.dto.request.food.AddonFoodUpdateRequest;
import com.food_delivery.restaurant.dto.request.food.FoodCreateRequest;
import com.food_delivery.restaurant.dto.request.food.FoodUpdateRequest;
import com.food_delivery.restaurant.dto.response.AddonFoodResponse;
import com.food_delivery.restaurant.dto.response.FoodResponse;
import com.food_delivery.restaurant.dto.response.PageResponse;

import java.math.BigDecimal;

public interface FoodService {
    /* ADDON_FOOD */
    AddonFoodResponse createAddonFood(AddonFoodCreateRequest request);

    AddonFoodResponse updateAddonFood(AddonFoodUpdateRequest request);

    void deleteAddonFood(String id);

    PageResponse<AddonFoodResponse> getAddonFoodsByRestaurantId(
            String restaurantId, int page, int limit
    );
    /* END ADDON_FOOD */

    /* FOOD */
    FoodResponse createFood(FoodCreateRequest request);

    FoodResponse updateFood(FoodUpdateRequest request);

    void deleteFood(String id);

    FoodResponse getFoodById(String id);

    PageResponse<FoodResponse> getFoodsByRestaurantId(
            String restaurantId, int page, int limit
    );

    PageResponse<FoodResponse> getFoodsByKeyword(
            String keyword, int page, int limit
    );

    PageResponse<FoodResponse> filterFoodsByCriteria(
            String cuisine,
            Double minStars,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int limit
    );

    void calculateRating(String foodId, int ratingStar);
    /* END FOOD */
}
