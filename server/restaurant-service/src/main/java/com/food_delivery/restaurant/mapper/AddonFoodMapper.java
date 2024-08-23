package com.food_delivery.restaurant.mapper;

import com.food_delivery.restaurant.dto.request.food.AddonFoodCreateRequest;
import com.food_delivery.restaurant.dto.request.food.AddonFoodUpdateRequest;
import com.food_delivery.restaurant.dto.response.AddonFoodResponse;
import com.food_delivery.restaurant.entity.AddonFood;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddonFoodMapper {
    AddonFood toAddonFood(AddonFoodCreateRequest request);

    AddonFoodResponse toAddonFoodResponse(AddonFood addonFood);

    @Mapping(target = "id", ignore = true)
    void updateAddonFood(@MappingTarget AddonFood addonFood, AddonFoodUpdateRequest request);
}
