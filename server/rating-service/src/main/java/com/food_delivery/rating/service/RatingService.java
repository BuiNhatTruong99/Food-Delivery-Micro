package com.food_delivery.rating.service;

import com.food_delivery.rating.dto.request.RatingCreateRequest;
import com.food_delivery.rating.dto.response.PageResponse;
import com.food_delivery.rating.dto.response.RatingResponse;

public interface RatingService {

    PageResponse<RatingResponse> getRatingsByRestaurantId(String restaurantId, int page, int limit);

    PageResponse<RatingResponse> getRatingsByFoodId(String foodId, int page, int limit);

    RatingResponse createRating(RatingCreateRequest ratingCreateRequest);

}
