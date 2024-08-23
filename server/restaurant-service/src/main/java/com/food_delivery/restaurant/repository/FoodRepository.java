package com.food_delivery.restaurant.repository;

import com.food_delivery.restaurant.entity.Food;
import org.bson.types.Decimal128;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepository extends MongoRepository<Food, String> {
    Optional<Food> findByNameAndRestaurantId(String name, String restaurantId);

    Page<Food> findAllByRestaurantId(String restaurantId, Pageable pageable);

    @Query(
            "{$or:  [{'name': {$regex: ?0, $options: 'i'}}," +
                    "{'category': {$regex: ?0, $options: 'i'}}]}"
    )
    Page<Food> findByKeyword(String keyword, Pageable pageable);

    @Query("{'category': {$regex: ?0, $options: 'i'}," +
            " 'averageStars': {$gte: ?1}, 'price': {$gte: ?2, $lte: ?3}}")
    Page<Food> filterByCriteria(
            String cuisine,
            Double minStars,
            Decimal128 minPrice,
            Decimal128 maxPrice,
            Pageable pageable
    );
}
