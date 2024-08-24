package com.food_delivery.rating.controller;

import com.food_delivery.rating.dto.request.RatingCreateRequest;
import com.food_delivery.rating.dto.response.ApiResponse;
import com.food_delivery.rating.dto.response.PageResponse;
import com.food_delivery.rating.dto.response.RatingResponse;
import com.food_delivery.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<RatingResponse>> createRating(
            @RequestBody RatingCreateRequest ratingCreateRequest
    ) {
        var rating = ratingService.createRating(ratingCreateRequest);
        return ResponseEntity.ok(ApiResponse.<RatingResponse>builder().data(rating).build());
    }

    @GetMapping("/food-ratings/{foodId}")
    public ResponseEntity<ApiResponse<PageResponse<RatingResponse>>> getRatingsByFoodId(
            @PathVariable String foodId,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit
    ) {
        var ratings = ratingService.getRatingsByFoodId(foodId, page, limit);
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<RatingResponse>>builder()
                        .data(ratings)
                        .build()
        );
    }

    @GetMapping("/restaurant-ratings/{restaurantId}")
    public ResponseEntity<ApiResponse<PageResponse<RatingResponse>>> getRatingsByRestaurantId(
            @PathVariable String restaurantId,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit
    ) {
        var ratings = ratingService.getRatingsByRestaurantId(restaurantId, page, limit);
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<RatingResponse>>builder()
                        .data(ratings)
                        .build()
        );
    }
}
