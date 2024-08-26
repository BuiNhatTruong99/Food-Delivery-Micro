package com.food_delivery.restaurant.controller;

import com.food_delivery.restaurant.dto.request.category.CategoryCreateRequest;
import com.food_delivery.restaurant.dto.request.category.CategoryUpdateRequest;
import com.food_delivery.restaurant.dto.response.ApiResponse;
import com.food_delivery.restaurant.dto.response.CategoryResponse;
import com.food_delivery.restaurant.service.CategoryService;
import com.food_delivery.restaurant.service.FoodService;
import com.food_delivery.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import kafka.RatingAdded;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class RestaurantInternalController {
    private final CategoryService categoryService;
    private final RestaurantService restaurantService;
    private final FoodService foodService;

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
        return ResponseEntity.ok(
                ApiResponse.<Boolean>builder()
                        .message("Restaurant disabled successfully")
                        .build());
    }

    @KafkaListener(topics = "rating-topic")
    public void calculateRatingStar(RatingAdded rating) {
        log.info("Received rating: {}", rating);
        if (rating.getRestaurantId() == null) {
            foodService.calculateRating(rating.getFoodId(), rating.getRatingStar());
        } else {
            restaurantService.calculateRating(rating.getRestaurantId(), rating.getRatingStar());
        }
    }
}
