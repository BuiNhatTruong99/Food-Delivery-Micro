package com.food_delivery.restaurant.service;

import com.food_delivery.restaurant.dto.request.restaurant.RestaurantCreateRequest;
import com.food_delivery.restaurant.dto.request.restaurant.RestaurantUpdateRequest;
import com.food_delivery.restaurant.dto.response.RestaurantResponse;

public interface RestaurantService {

    RestaurantResponse getRestaurantById(String id);

    RestaurantResponse createRestaurant(RestaurantCreateRequest request);

    RestaurantResponse updateRestaurant(RestaurantUpdateRequest request);

    void disableRestaurant(String id);
}
