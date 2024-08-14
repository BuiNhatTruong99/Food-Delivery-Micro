package com.food_delivery.restaurant.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "restaurant")
public class Restaurant {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("address")
    private String address;

    @Field("total_stars")
    private Double averageStars;

    @Field("total_reviews")
    private Integer totalReviews;

    @Field("tags")
    private List<RestaurantTag> tags = new ArrayList<>();

    @Field("owner_id")
    private Integer ownerId;

}
