package com.food_delivery.rating.mapper;

import com.food_delivery.rating.dto.request.RatingCreateRequest;
import com.food_delivery.rating.dto.response.RatingResponse;
import com.food_delivery.rating.entity.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    Rating toRating(RatingCreateRequest ratingCreateRequest);

    RatingResponse toRatingResponse(Rating rating);
}
