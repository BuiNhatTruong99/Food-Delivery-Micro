package com.food_delivery.rating.service;

import com.food_delivery.rating.client.UserService;
import com.food_delivery.rating.dto.request.RatingCreateRequest;
import com.food_delivery.rating.dto.response.PageResponse;
import com.food_delivery.rating.dto.response.RatingResponse;
import com.food_delivery.rating.exception.DuplicateResourceException;
import com.food_delivery.rating.exception.ErrorCode;
import com.food_delivery.rating.mapper.RatingMapper;
import com.food_delivery.rating.repository.RatingRepository;
import kafka.RatingAdded;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final UserService userService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public PageResponse<RatingResponse> getRatingsByRestaurantId(
            String restaurantId, int page, int limit
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        var ratings = ratingRepository.findAllByRestaurantId(restaurantId, pageable);
        return PageResponse.<RatingResponse>builder()
                .page(page)
                .limit(limit)
                .total(ratings.getTotalPages())
                .data(ratings.getContent().stream()
                        .map(ratingMapper::toRatingResponse)
                        .toList())
                .build();
    }

    @Override
    public PageResponse<RatingResponse> getRatingsByFoodId(String foodId, int page, int limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        var ratings = ratingRepository.findAllByFoodId(foodId, pageable);
        return PageResponse.<RatingResponse>builder()
                .page(page)
                .limit(limit)
                .total(ratings.getTotalPages())
                .data(ratings.getContent().stream()
                        .map(ratingMapper::toRatingResponse)
                        .toList())
                .build();
    }

    @Override
    public RatingResponse createRating(RatingCreateRequest ratingCreateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userClaimId = (String) jwt.getClaims().get("id");
        Integer userId = Integer.parseInt(userClaimId);

        if (ratingRepository.existsByUserIdAndFoodId(userId, ratingCreateRequest.getFoodId()) ||
                ratingRepository.existsByUserIdAndRestaurantId(
                        userId, ratingCreateRequest.getRestaurantId()
                )
        ) {
            throw new DuplicateResourceException(ErrorCode.ERR_RATING_EXISTS);
        }
        var user = userService.getUser(userId);
        var rating = ratingMapper.toRating(ratingCreateRequest);
        rating.setUserId(user.getData().getId());
        rating.setUsername(user.getData().getFullName());
        rating.setAvatarUrl(user.getData().getImageUrl());
        ratingRepository.save(rating);
        // publish rating to kafka
        RatingAdded ratingAdded = RatingAdded.builder()
                .foodId(ratingCreateRequest.getFoodId())
                .restaurantId(ratingCreateRequest.getRestaurantId())
                .ratingStar(ratingCreateRequest.getRatingStar())
                .build();
        kafkaTemplate.send("rating-topic", ratingAdded);
        return ratingMapper.toRatingResponse(rating);
    }
}
