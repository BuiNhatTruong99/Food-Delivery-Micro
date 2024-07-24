package com.food_delivery.profile.exception;

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
    ERR_UNAUTHENTICATED("Unauthenticated", HttpStatus.UNAUTHORIZED),
    ERR_UNAUTHORIZED("Unauthorized", HttpStatus.UNAUTHORIZED),

    // user
    ERR_PROFILE_NOT_FOUND("Profile not found", HttpStatus.NOT_FOUND);



    private final String message;
    private final HttpStatusCode httpStatusCode;
}
