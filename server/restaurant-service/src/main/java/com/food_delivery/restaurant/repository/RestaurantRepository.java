package com.food_delivery.restaurant.repository;

import com.food_delivery.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    Optional<Restaurant> findByNameAndAddress(String name, String address);

    @Query(
            "{$or:  [{'name': {$regex: ?0, $options: 'i'}}," +
                    "{'tags': {$regex: ?0, $options: 'i'}}]}"
    )
    Page<Restaurant> findByKeyword(String keyword, Pageable pageable);

    @Query("{'tags': {$regex: ?0, $options: 'i'}, 'averageStars': {$gte: ?1}}")
    Page<Restaurant> filterRestaurantsByCriteria(
            String cuisine,
            Double minStars,
            Pageable pageable
    );

    @Query("{ 'averageStars': { $gte: ?0 } }")
    Page<Restaurant> filterByStars(Double minStars, Pageable pageable);
}
