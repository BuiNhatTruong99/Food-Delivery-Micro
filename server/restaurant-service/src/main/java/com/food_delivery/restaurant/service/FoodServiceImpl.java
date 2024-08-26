package com.food_delivery.restaurant.service;

import com.food_delivery.restaurant.dto.request.food.AddonFoodCreateRequest;
import com.food_delivery.restaurant.dto.request.food.AddonFoodUpdateRequest;
import com.food_delivery.restaurant.dto.request.food.FoodCreateRequest;
import com.food_delivery.restaurant.dto.request.food.FoodUpdateRequest;
import com.food_delivery.restaurant.dto.response.AddonFoodResponse;
import com.food_delivery.restaurant.dto.response.FoodResponse;
import com.food_delivery.restaurant.dto.response.PageResponse;
import com.food_delivery.restaurant.entity.AddonFood;
import com.food_delivery.restaurant.entity.Food;
import com.food_delivery.restaurant.exception.DuplicateResourceException;
import com.food_delivery.restaurant.exception.ErrorCode;
import com.food_delivery.restaurant.exception.ResourceNotFoundException;
import com.food_delivery.restaurant.mapper.AddonFoodMapper;
import com.food_delivery.restaurant.mapper.FoodMapper;
import com.food_delivery.restaurant.repository.AddonFoodRepository;
import com.food_delivery.restaurant.repository.FoodRepository;
import com.food_delivery.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Decimal128;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final AddonFoodRepository addonFoodRepository;
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final AddonFoodMapper addonFoodMapper;
    private final FoodMapper foodMapper;

    /* Addon Food */
    @Override
    public AddonFoodResponse createAddonFood(AddonFoodCreateRequest request) {
        log.info("ADDON_FOOD: Start create addon food: {}", request.getName());
        restaurantRepository
                .findById(request.getRestaurantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorCode.ERR_RESTAURANT_NOT_FOUND));
        Optional<AddonFood> addonFoodOptional = addonFoodRepository
                .findByName(request.getName());
        if (addonFoodOptional.isPresent()) {
            log.error("ADDON_FOOD: Duplicate addon food: {}", request.getName());
            throw new DuplicateResourceException(ErrorCode.ERR_ADDON_FOOD_DUPLICATE);
        }
        var addonFood = addonFoodMapper.toAddonFood(request);
        addonFoodRepository.save(addonFood);
        log.info("ADDON_FOOD: End create addon food: {}", request.getName());
        return addonFoodMapper.toAddonFoodResponse(addonFood);
    }

    @Override
    public AddonFoodResponse updateAddonFood(AddonFoodUpdateRequest request) {
        log.info("ADDON_FOOD: Start update addon food: {}", request.getName());
        var addonFood = addonFoodRepository
                .findById(request.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorCode.ERR_ADDON_FOOD_NOT_FOUND));
        addonFoodMapper.updateAddonFood(addonFood, request);
        addonFoodRepository.save(addonFood);
        log.info("ADDON_FOOD: End update addon food: {}", request.getName());
        return addonFoodMapper.toAddonFoodResponse(addonFood);
    }

    @Override
    public void deleteAddonFood(String id) {
        log.info("ADDON_FOOD: Start delete addon food: {}", id);
        var addonFood = addonFoodRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorCode.ERR_ADDON_FOOD_NOT_FOUND));
        addonFoodRepository.delete(addonFood);
        log.info("ADDON_FOOD: End delete addon food: {}", id);
    }

    @Override
    public PageResponse<AddonFoodResponse> getAddonFoodsByRestaurantId(
            String restaurantId, int page, int limit
    ) {
        var restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorCode.ERR_RESTAURANT_NOT_FOUND));
        Pageable pageable = PageRequest.of(page - 1, limit);
        var addonFoods = addonFoodRepository
                .findAllByRestaurantId(restaurant.getId(), pageable);
        return PageResponse.<AddonFoodResponse>builder()
                .page(page)
                .limit(limit)
                .total(addonFoods.getTotalPages())
                .data(addonFoods.getContent().stream()
                        .map(addonFoodMapper::toAddonFoodResponse)
                        .toList())
                .build();
    }

    private List<AddonFood> getAddonFoodByIds(List<String> addonFoodIds) {
        return addonFoodIds.stream()
                .map(id -> addonFoodRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                ErrorCode.ERR_ADDON_FOOD_NOT_FOUND
                        )))
                .toList();
    }
    /* End Addon Food */

    /* Food */

    @Override
    public FoodResponse createFood(FoodCreateRequest request) {
        log.info("FOOD: Start create food: {} for restaurant: {}"
                , request.getName(), request.getRestaurantId());
        restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorCode.ERR_RESTAURANT_NOT_FOUND));
        Optional<Food> foodOptional = foodRepository
                .findByNameAndRestaurantId(request.getName(), request.getRestaurantId());
        if (foodOptional.isPresent()) {
            log.error("FOOD: Duplicate food: {}", request.getName());
            throw new DuplicateResourceException(ErrorCode.ERR_FOOD_DUPLICATE);
        }
        var food = foodMapper.toFood(request);
        food.setAddonFoods(getAddonFoodByIds(request.getAddonFoodIds()));
        foodRepository.save(food);
        log.info("FOOD: End create food: {} for restaurant: {}"
                , request.getName(), request.getRestaurantId());
        return foodMapper.toFoodResponse(food);
    }

    @Override
    public FoodResponse updateFood(FoodUpdateRequest request) {
        log.info("FOOD: Start update food: {}", request.getName());
        var food = foodRepository
                .findById(request.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorCode.ERR_FOOD_NOT_FOUND));
        foodMapper.updateFood(food, request);
        food.setAddonFoods(getAddonFoodByIds(request.getAddonFoodIds()));
        foodRepository.save(food);
        log.info("FOOD: End update food: {}", request.getName());
        return foodMapper.toFoodResponse(food);
    }

    @Override
    public void deleteFood(String id) {
        log.info("FOOD: Start delete food: {}", id);
        var food = foodRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorCode.ERR_FOOD_NOT_FOUND));
        foodRepository.delete(food);
        log.info("FOOD: End delete food: {}", id);
    }

    @Override
    public FoodResponse getFoodById(String id) {
        var food = foodRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorCode.ERR_FOOD_NOT_FOUND));
        return foodMapper.toFoodResponse(food);
    }

    @Override
    public PageResponse<FoodResponse> getFoodsByRestaurantId(String restaurantId, int page, int limit) {
        log.info("FOOD: Start get foods by restaurant: {}", restaurantId);
        var restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorCode.ERR_RESTAURANT_NOT_FOUND));
        Pageable pageable = PageRequest.of(page - 1, limit);
        var foods = foodRepository
                .findAllByRestaurantId(restaurant.getId(), pageable);
        log.info("FOOD: End get foods by restaurant: {}", restaurantId);
        return PageResponse.<FoodResponse>builder()
                .page(page)
                .limit(limit)
                .total(foods.getTotalPages())
                .data(foods.getContent().stream()
                        .map(foodMapper::toFoodResponse)
                        .toList())
                .build();
    }

    @Override
    public PageResponse<FoodResponse> getFoodsByKeyword(String keyword, int page, int limit) {
        log.info("FOOD: Find by keyword: {}", keyword);
        Pageable pageable = PageRequest.of(page - 1, limit);
        var foods = foodRepository
                .findByKeyword(keyword, pageable);
        log.info("FOOD: End find by keyword: {}", keyword);
        return PageResponse.<FoodResponse>builder()
                .page(page)
                .limit(limit)
                .total(foods.getTotalPages())
                .data(foods.getContent().stream()
                        .map(foodMapper::toFoodResponse)
                        .toList())
                .build();
    }

    @Override
    public PageResponse<FoodResponse> filterFoodsByCriteria(
            String cuisine,
            Double minStars,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page, int limit
    ) {
        log.info("FOOD: Start filter food by criteria");
        Sort sort = Sort.by(Sort.Direction.ASC, "price");
        Decimal128 minPriceDecimal = new Decimal128(minPrice);
        Decimal128 maxPriceDecimal = new Decimal128(maxPrice);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        var foods = foodRepository
                .filterByCriteria(cuisine, minStars, minPriceDecimal, maxPriceDecimal, pageable);
        log.info("FOOD: End filter food by criteria");
        return PageResponse.<FoodResponse>builder()
                .page(page)
                .limit(limit)
                .total(foods.getTotalPages())
                .data(foods.getContent().stream()
                        .map(foodMapper::toFoodResponse)
                        .toList())
                .build();
    }


    @Override
    public void calculateRating(String foodId, int ratingStar) {
        var food = foodRepository
                .findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_FOOD_NOT_FOUND));
        BigDecimal rating = new BigDecimal(ratingStar);
        BigDecimal totalStars =
                food.getAverageStars().multiply(new BigDecimal(food.getTotalReviews()));
        BigDecimal newTotalStars = totalStars.add(rating);
        BigDecimal newTotalReviews = new BigDecimal(food.getTotalReviews() + 1);
        BigDecimal newAverageStars = newTotalStars.divide(newTotalReviews, 1, RoundingMode.HALF_UP);
        food.setAverageStars(newAverageStars);
        food.setTotalReviews(food.getTotalReviews() + 1);
        foodRepository.save(food);
    }
}
