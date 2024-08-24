package com.food_delivery.rating.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException {
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatusCode = errorCode.getHttpStatusCode();
    }

    private final HttpStatusCode httpStatusCode;
}
