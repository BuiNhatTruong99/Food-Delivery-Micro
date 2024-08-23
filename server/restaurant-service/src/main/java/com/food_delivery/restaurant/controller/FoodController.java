package com.food_delivery.restaurant.controller;

import com.food_delivery.restaurant.dto.request.food.AddonFoodCreateRequest;
import com.food_delivery.restaurant.dto.request.food.AddonFoodUpdateRequest;
import com.food_delivery.restaurant.dto.request.food.FoodCreateRequest;
import com.food_delivery.restaurant.dto.request.food.FoodUpdateRequest;
import com.food_delivery.restaurant.dto.response.AddonFoodResponse;
import com.food_delivery.restaurant.dto.response.ApiResponse;
import com.food_delivery.restaurant.dto.response.FoodResponse;
import com.food_delivery.restaurant.dto.response.PageResponse;
import com.food_delivery.restaurant.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    /* ADDON_FOOD */
    @GetMapping("/get/addons/restaurant/{restaurantId}")
    public ResponseEntity<ApiResponse<PageResponse<AddonFoodResponse>>>
    findAddonFoodsByRestaurantId(
            @PathVariable String restaurantId,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit
    ) {
        var data = foodService.getAddonFoodsByRestaurantId(restaurantId, page, limit);
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<AddonFoodResponse>>builder()
                        .data(data)
                        .build()
        );
    }

    @PostMapping("/create/addon")
    public ResponseEntity<ApiResponse<AddonFoodResponse>> createAddonFood(
            @Valid
            @RequestBody AddonFoodCreateRequest request
    ) {
        var data = foodService.createAddonFood(request);
        return ResponseEntity.ok(ApiResponse.<AddonFoodResponse>builder().data(data).build());
    }

    @PutMapping("/update/addon")
    public ResponseEntity<ApiResponse<AddonFoodResponse>> updateAddonFood(
            @Valid
            @RequestBody AddonFoodUpdateRequest request
    ) {
        var data = foodService.updateAddonFood(request);
        return ResponseEntity.ok(ApiResponse.<AddonFoodResponse>builder().data(data).build());
    }

    @DeleteMapping("/delete/addon/{addonFoodId}")
    public ResponseEntity<ApiResponse<Void>> deleteAddonFood(
            @PathVariable String addonFoodId
    ) {
        foodService.deleteAddonFood(addonFoodId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .message("Addon food deleted successfully")
                        .build());
    }
    /* END ADDON_FOOD */

    /* FOOD */
    @GetMapping("/get/foods/restaurant/{restaurantId}")
    public ResponseEntity<ApiResponse<PageResponse<FoodResponse>>> findFoodsByRestaurantId(
            @PathVariable String restaurantId,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit
    ) {
        var data = foodService.getFoodsByRestaurantId(restaurantId, page, limit);
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<FoodResponse>>builder()
                        .data(data)
                        .build()
        );
    }

    @GetMapping("/get/food/{foodId}")
    public ResponseEntity<ApiResponse<FoodResponse>> getFoodById(
            @PathVariable String foodId
    ) {
        var data = foodService.getFoodById(foodId);
        return ResponseEntity.ok(ApiResponse.<FoodResponse>builder().data(data).build());
    }

    @PostMapping("/create/food")
    public ResponseEntity<ApiResponse<FoodResponse>> createFood(
            @Valid
            @RequestBody FoodCreateRequest request
    ) {
        var data = foodService.createFood(request);
        return ResponseEntity.ok(ApiResponse.<FoodResponse>builder().data(data).build());
    }

    @PutMapping("/update/food")
    public ResponseEntity<ApiResponse<FoodResponse>> updateFood(
            @Valid
            @RequestBody FoodUpdateRequest request
    ) {
        var data = foodService.updateFood(request);
        return ResponseEntity.ok(ApiResponse.<FoodResponse>builder().data(data).build());
    }

    @DeleteMapping("/delete/food/{foodId}")
    public ResponseEntity<ApiResponse<Void>> deleteFood(
            @PathVariable String foodId
    ) {
        foodService.deleteFood(foodId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .message("Food deleted successfully")
                        .build());
    }

    @GetMapping("/search/foods")
    public ResponseEntity<ApiResponse<PageResponse<FoodResponse>>> searchFoods(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        var data = foodService.getFoodsByKeyword(keyword, page, limit);
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<FoodResponse>>builder()
                        .data(data)
                        .build()
        );
    }

    @GetMapping("/filter/foods")
    public ResponseEntity<ApiResponse<PageResponse<FoodResponse>>> filterFoodsByCriteria(
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false, defaultValue = "0.0") Double minStars,
            @RequestParam(required = false, defaultValue = "0.0") BigDecimal minPrice,
            @RequestParam(required = false, defaultValue = "9999.99") BigDecimal maxPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        var data = foodService.filterFoodsByCriteria(
                cuisine, minStars, minPrice, maxPrice, page, limit
        );
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<FoodResponse>>builder()
                        .data(data)
                        .build()
        );
    }
}
