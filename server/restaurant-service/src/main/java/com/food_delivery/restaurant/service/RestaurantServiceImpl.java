package com.food_delivery.restaurant.service;

import com.food_delivery.restaurant.dto.request.restaurant.RestaurantCreateRequest;
import com.food_delivery.restaurant.dto.request.restaurant.RestaurantUpdateRequest;
import com.food_delivery.restaurant.dto.response.PageResponse;
import com.food_delivery.restaurant.dto.response.RestaurantResponse;
import com.food_delivery.restaurant.entity.Restaurant;
import com.food_delivery.restaurant.exception.DuplicateResourceException;
import com.food_delivery.restaurant.exception.ErrorCode;
import com.food_delivery.restaurant.exception.ResourceNotFoundException;
import com.food_delivery.restaurant.mapper.RestaurantMapper;
import com.food_delivery.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CategoryService categoryService;
    private final RestaurantMapper restaurantMapper;

    @Override
    public RestaurantResponse getRestaurantById(String id) {
        return restaurantMapper.toRestaurantResponse(
                restaurantRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                ErrorCode.ERR_RESTAURANT_NOT_FOUND
                        ))
        );
    }

    @Override
    public RestaurantResponse createRestaurant(RestaurantCreateRequest request) {
        log.info("RESTAURANT: Start process creating for {}", request.getName());
        Optional<Restaurant> restaurantOptional =
                restaurantRepository.findByNameAndAddress(
                        request.getName(),
                        request.getAddress()
                );
        if (restaurantOptional.isPresent()) {
            log.error("RESTAURANT: Restaurant already exists");
            throw new DuplicateResourceException(ErrorCode.ERR_RESTAURANT_DUPLICATE);
        }
        log.info("RESTAURANT: Get userId from security context ");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = (String) jwt.getClaims().get("id");
        var newRestaurant = restaurantMapper.toRestaurant(request);
        newRestaurant.setTags(categoryService.getCategoriesNameByIds(request.getCategoryIds()));
        newRestaurant.setShippingTime(
                request.getMinShippingTime() + "-" + request.getMaxShippingTime()
        );
        newRestaurant.setAverageShippingTime(
                ((double) request.getMinShippingTime() + request.getMaxShippingTime()) / 2
        );
        newRestaurant.setOwnerId(Integer.valueOf(userId));
        restaurantRepository.save(newRestaurant);
        log.info("RESTAURANT: End process creating for {}", request.getName());
        return restaurantMapper.toRestaurantResponse(newRestaurant);
    }

    @Override
    @Transactional
    public RestaurantResponse updateRestaurant(RestaurantUpdateRequest request) {
        log.info("RESTAURANT: Start process updating for {}", request.getId());
        var restaurant = restaurantRepository
                .findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.ERR_RESTAURANT_NOT_FOUND
                ));
        restaurantMapper.updateRestaurant(restaurant, request);
        restaurant.setTags(categoryService.getCategoriesNameByIds(request.getCategoryIds()));
        restaurant.setShippingTime(
                request.getMinShippingTime() + "-" + request.getMaxShippingTime()
        );
        restaurant.setAverageShippingTime(
                ((double) request.getMinShippingTime() + request.getMaxShippingTime()) / 2
        );
        restaurantRepository.save(restaurant);
        log.info("RESTAURANT: End process updating for {}", request.getName());
        return restaurantMapper.toRestaurantResponse(restaurant);
    }

    @Override
    @Transactional
    public void disableRestaurant(String id) {
        log.info("RESTAURANT: Start process disable for restaurant {}", id);
        var restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.ERR_RESTAURANT_NOT_FOUND
                ));
        restaurant.setDisabled(true);
        restaurantRepository.save(restaurant);
        log.info("RESTAURANT: End process disable for {}", id);
    }

    @Override
    public PageResponse<RestaurantResponse> findRestaurantsByKeyword(
            String keyword,
            int page,
            int limit
    ) {
        log.info("RESTAURANT: Find by keyword {}", keyword);
        Pageable pageable = PageRequest.of(page - 1, limit);
        var restaurants = restaurantRepository.findByKeyword(keyword, pageable);
        return PageResponse.<RestaurantResponse>builder()
                .page(page)
                .limit(limit)
                .total(restaurants.getTotalPages())
                .data(restaurants.getContent().stream()
                        .map(restaurantMapper::toRestaurantResponse)
                        .toList())
                .build()
                ;
    }

    @Override
    public PageResponse<RestaurantResponse> filterRestaurantsByCriteria(
            String cuisine,
            Double minStars,
            String shortBy,
            int page,
            int limit
    ) {
        Sort sort;
        if (shortBy.equalsIgnoreCase("shippingFee")) {
            sort = Sort.by(Sort.Direction.ASC, "shippingFee");
        } else if (shortBy.equalsIgnoreCase("shippingTime")) {
            sort = Sort.by(Sort.Direction.ASC, "averageShippingTime");
        } else {
            sort = Sort.unsorted();
        }
        log.info("RESTAURANT: Sort by {}", sort);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Restaurant> restaurants;
        if (cuisine == null) {
            restaurants = restaurantRepository
                    .filterByStars(minStars, pageable);
        } else {
            restaurants = restaurantRepository
                    .filterRestaurantsByCriteria(cuisine, minStars, pageable);
        }
        log.info("RESTAURANT: End process filter by criteria");
        return PageResponse.<RestaurantResponse>builder()
                .page(page)
                .limit(limit)
                .total(restaurants.getTotalPages())
                .data(restaurants.getContent().stream()
                        .map(restaurantMapper::toRestaurantResponse)
                        .toList())
                .build();
    }

}
