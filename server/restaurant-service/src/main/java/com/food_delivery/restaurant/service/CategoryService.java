package com.food_delivery.restaurant.service;

import com.food_delivery.restaurant.dto.request.category.CategoryCreateRequest;
import com.food_delivery.restaurant.dto.request.category.CategoryUpdateRequest;
import com.food_delivery.restaurant.dto.response.CategoryResponse;
import com.food_delivery.restaurant.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryCreateRequest request);

    CategoryResponse updateCategory(CategoryUpdateRequest request);

    List<CategoryResponse> getCategories();

    List<Category> getCategoriesByIds(List<String> categoryIds);

    void deleteCategory(String id);
}
