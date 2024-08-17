package com.food_delivery.restaurant.controller;

import com.food_delivery.restaurant.dto.request.category.CategoryCreateRequest;
import com.food_delivery.restaurant.dto.request.category.CategoryUpdateRequest;
import com.food_delivery.restaurant.dto.response.ApiResponse;
import com.food_delivery.restaurant.dto.response.CategoryResponse;
import com.food_delivery.restaurant.service.CategoryService;
import com.food_delivery.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class RestaurantInternalController {
    private final CategoryService categoryService;
    private final RestaurantService restaurantService;

    /* Category API */
    @PostMapping("/category/create")
    public ResponseEntity<ApiResponse<CategoryResponse>> createTag(
            @Valid
            @RequestBody CategoryCreateRequest request
    ) {
        var data = categoryService.createCategory(request);
        return ResponseEntity.ok(ApiResponse.<CategoryResponse>builder().data(data).build());
    }

    @PutMapping("/category/update")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @Valid
            @RequestBody CategoryUpdateRequest request
    ) {
        var data = categoryService.updateCategory(request);
        return ResponseEntity.ok(ApiResponse.<CategoryResponse>builder().data(data).build());
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<?>> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.<Boolean>builder().data(true).build());
    }

    /* Restaurant API */
    @PatchMapping("/restaurant/disable/{restaurantId}")
    public ResponseEntity<ApiResponse<?>> disableRestaurant(@PathVariable String restaurantId) {
        restaurantService.disableRestaurant(restaurantId);
        return ResponseEntity.ok(ApiResponse.<Boolean>builder().data(true).build());
    }
}
