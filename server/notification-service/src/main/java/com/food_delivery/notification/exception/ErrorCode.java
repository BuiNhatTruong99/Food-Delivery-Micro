package com.food_delivery.notification.exception;

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
    ERR_UNAUTHORIZED("You don't have permission", HttpStatus.FORBIDDEN),

    // email
    ERR_CANNOT_SEND_EMAIL("Cannot send email", HttpStatus.BAD_REQUEST),
    ;


    private final String message;
    private final HttpStatusCode httpStatusCode;
}
