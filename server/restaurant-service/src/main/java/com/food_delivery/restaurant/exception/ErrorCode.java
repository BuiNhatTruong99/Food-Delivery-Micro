package com.food_delivery.restaurant.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // common
    ERR_INTERNAL_SERVER_ERROR("Oops! Some things went wrong", HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_TOKEN_EXPIRED("Your session has expired. Please log in again", HttpStatus.UNAUTHORIZED),
    ERR_TOKEN_INVALID("Session is invalid. Please log in again", HttpStatus.UNAUTHORIZED),
    ERR_UNAUTHENTICATED("You need to be logged in", HttpStatus.UNAUTHORIZED),
    ERR_UNAUTHORIZED("Sorry! You don't have permission", HttpStatus.FORBIDDEN),

    // restaurant
    ERR_RESTAURANT_DUPLICATE("Restaurant information already exists", HttpStatus.CONFLICT),
    ERR_RESTAURANT_NOT_FOUND("We couldn't find this restaurant", HttpStatus.NOT_FOUND),

    // category
    ERR_CATEGORY_DUPLICATE("This category already exists", HttpStatus.CONFLICT),
    ERR_CATEGORY_NOT_FOUND("Category not found", HttpStatus.NOT_FOUND),

    // food
    ERR_ADDON_FOOD_NOT_FOUND("We couldn't find this addon food", HttpStatus.NOT_FOUND),
    ERR_ADDON_FOOD_DUPLICATE("Addon food already exists", HttpStatus.CONFLICT),
    ERR_FOOD_NOT_FOUND("We couldn't find this food", HttpStatus.NOT_FOUND),
    ERR_FOOD_DUPLICATE("This food already exists", HttpStatus.CONFLICT),
    ;
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
