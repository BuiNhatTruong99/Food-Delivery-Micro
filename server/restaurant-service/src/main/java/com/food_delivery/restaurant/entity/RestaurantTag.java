package com.food_delivery.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestaurantTag {

    // countries tag
    ITALIAN("ITALIAN"),
    MEXICAN("MEXICAN"),
    THAI("THAI"),
    CHINESE("CHINESE"),
    JAPANESE("JAPANESE"),
    KOREAN("KOREAN"),
    VIETNAMESE("VIETNAMESE"),
    FRENCH("FRENCH"),
    AMERICAN("AMERICAN"),

    // cuisines tag
    FAST_FOOD("FAST FOOD"),
    SEAFOOD("SEAFOOD"),
    CHICKEN("CHICKEN"),
    DESSERT("DESSERT"),
    COFFEE("COFFEE"),
    BAKERY("BAKERY"),
    PIZZA("PIZZA"),
    BURGER("BURGER"),
    SUSHI("SUSHI"),
    STEAK("STEAK"),
    TACOS("TACOS");

    private final String description;
}
