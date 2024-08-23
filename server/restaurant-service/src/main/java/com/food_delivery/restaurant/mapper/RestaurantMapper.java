package com.food_delivery.restaurant.mapper;

import com.food_delivery.restaurant.dto.request.restaurant.RestaurantCreateRequest;
import com.food_delivery.restaurant.dto.request.restaurant.RestaurantUpdateRequest;
import com.food_delivery.restaurant.dto.response.RestaurantResponse;
import com.food_delivery.restaurant.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    Restaurant toRestaurant(RestaurantCreateRequest request);

    @Mapping(target = "id", ignore = true)
    void updateRestaurant(@MappingTarget Restaurant restaurant, RestaurantUpdateRequest request);

    RestaurantResponse toRestaurantResponse(Restaurant restaurant);
}
