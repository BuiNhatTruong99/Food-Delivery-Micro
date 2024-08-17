package com.food_delivery.restaurant.controller;

import com.food_delivery.restaurant.dto.request.restaurant.RestaurantCreateRequest;
import com.food_delivery.restaurant.dto.request.restaurant.RestaurantUpdateRequest;
import com.food_delivery.restaurant.dto.response.ApiResponse;
import com.food_delivery.restaurant.dto.response.CategoryResponse;
import com.food_delivery.restaurant.dto.response.RestaurantResponse;
import com.food_delivery.restaurant.service.CategoryService;
import com.food_delivery.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final CategoryService categoryService;

    /* CATEGORY */
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getTags() {
        var data = categoryService.getCategories();
        return ResponseEntity.ok(ApiResponse.<List<CategoryResponse>>builder().data(data).build());
    }

    /* RESTAURANT */
    @GetMapping("/get/{restaurantId}")
    public ResponseEntity<ApiResponse<RestaurantResponse>> getRestaurantById(
            @PathVariable String restaurantId
    ) {
        var data = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(ApiResponse.<RestaurantResponse>builder().data(data).build());
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<RestaurantResponse>> createRestaurant(
            @Valid
            @RequestBody RestaurantCreateRequest request
    ) {
        var data = restaurantService.createRestaurant(request);
        return ResponseEntity.ok(ApiResponse.<RestaurantResponse>builder().data(data).build());
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<RestaurantResponse>> updateRestaurant(
            @Valid
            @RequestBody RestaurantUpdateRequest request
    ) {
        var data = restaurantService.updateRestaurant(request);
        return ResponseEntity.ok(ApiResponse.<RestaurantResponse>builder().data(data).build());
    }

}
