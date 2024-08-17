package com.food_delivery.restaurant.repository;

import com.food_delivery.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    Optional<Restaurant> findByNameAndAddress(String name, String address);

    Page<Restaurant> findByNameContainingIgnoreCaseOrTagsContainsIgnoreCase(String name, String tag, Pageable pageable);
}
