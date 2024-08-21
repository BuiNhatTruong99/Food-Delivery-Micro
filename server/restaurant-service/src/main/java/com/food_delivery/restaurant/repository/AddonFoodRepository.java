package com.food_delivery.restaurant.repository;

import com.food_delivery.restaurant.entity.AddonFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddonFoodRepository extends MongoRepository<AddonFood, String> {
    Optional<AddonFood> findByName(String name);

    Page<AddonFood> findAllByRestaurantId(String restaurantId, Pageable pageable);
}
