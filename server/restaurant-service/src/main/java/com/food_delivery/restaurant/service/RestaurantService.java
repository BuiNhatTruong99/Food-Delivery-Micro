package com.food_delivery.restaurant.service;

import com.food_delivery.restaurant.dto.request.restaurant.RestaurantCreateRequest;
import com.food_delivery.restaurant.dto.request.restaurant.RestaurantUpdateRequest;
import com.food_delivery.restaurant.dto.response.PageResponse;
import com.food_delivery.restaurant.dto.response.RestaurantResponse;

public interface RestaurantService {

    RestaurantResponse getRestaurantById(String id);

    RestaurantResponse createRestaurant(RestaurantCreateRequest request);

    RestaurantResponse updateRestaurant(RestaurantUpdateRequest request);

    void disableRestaurant(String id);

    PageResponse<RestaurantResponse> findRestaurantsByKeyword(String keyword, int page, int limit);

    PageResponse<RestaurantResponse> filterRestaurantsByCriteria(
            String cuisine,
            Double minStars,
            String shortBy,
            int page,
            int limit
    );
}
