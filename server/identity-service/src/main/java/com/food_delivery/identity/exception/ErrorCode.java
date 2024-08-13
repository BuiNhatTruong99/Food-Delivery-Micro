package com.food_delivery.identity.exception;

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

    // user
    ERR_USER_NOT_FOUND("We couldn't find your account", HttpStatus.NOT_FOUND),
    ERR_USER_DUPLICATE("An account with this email already exists", HttpStatus.CONFLICT),
    ERR_USER_INVALID_CREDENTIALS("Email or password is incorrect",
            HttpStatus.BAD_REQUEST),
    ERR_USER_PROVIDER_NOT_SUPPORTED("This account needs to be logged in with Google", HttpStatus.CONFLICT),

    // otp
    ERR_OTP_NOT_FOUND("Your OTP is incorrect. Please try again", HttpStatus.NOT_FOUND),
    ERR_OTP_EXPIRED("Your OTP has expired. Please request a new one", HttpStatus.BAD_REQUEST),

    // address
    ERR_ADDRESS_NOT_FOUND("We couldn't find your address", HttpStatus.NOT_FOUND),
    ;
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
