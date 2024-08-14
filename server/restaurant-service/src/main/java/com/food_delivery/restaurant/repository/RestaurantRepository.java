package com.food_delivery.restaurant.repository;

import com.food_delivery.restaurant.entity.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
}
