package com.food_delivery.restaurant.mapper;

import com.food_delivery.restaurant.dto.request.category.CategoryCreateRequest;
import com.food_delivery.restaurant.dto.response.CategoryResponse;
import com.food_delivery.restaurant.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryCreateRequest request);

    CategoryResponse toCategoryResponse(Category category);
}
