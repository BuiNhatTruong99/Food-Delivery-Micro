package com.food_delivery.restaurant.mapper;

import com.food_delivery.restaurant.dto.request.food.FoodCreateRequest;
import com.food_delivery.restaurant.dto.request.food.FoodUpdateRequest;
import com.food_delivery.restaurant.dto.response.FoodResponse;
import com.food_delivery.restaurant.entity.Food;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    Food toFood(FoodCreateRequest foodCreateRequest);

    FoodResponse toFoodResponse(Food food);

    @Mapping(target = "id", ignore = true)
    void updateFood(@MappingTarget Food food, FoodUpdateRequest foodUpdateRequest);
}
