package com.food_delivery.identity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // common
    ERR_INTERNAL_SERVER_ERROR("Some things went wrong", HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_TOKEN_EXPIRED("Token expired", HttpStatus.UNAUTHORIZED),
    ERR_TOKEN_INVALID("Invalid token", HttpStatus.UNAUTHORIZED),
    ERR_UNAUTHENTICATED("Unauthenticated", HttpStatus.UNAUTHORIZED),
    ERR_UNAUTHORIZED("You dont have permission", HttpStatus.FORBIDDEN),

    // user
    ERR_USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    ERR_USER_DUPLICATE("User already exists", HttpStatus.CONFLICT),
    ERR_USER_INVALID_CREDENTIALS("Invalid email or password", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatusCode httpStatusCode;
}
