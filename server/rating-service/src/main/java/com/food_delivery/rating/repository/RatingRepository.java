package com.food_delivery.rating.repository;

import com.food_delivery.rating.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    Page<Rating> findAllByRestaurantId(String restaurantId, Pageable pageable);

    Page<Rating> findAllByFoodId(String foodId, Pageable pageable);

    boolean existsByUserIdAndFoodId(Integer userId, String foodId);

    boolean existsByUserIdAndRestaurantId(Integer userId, String restaurantId);

}
