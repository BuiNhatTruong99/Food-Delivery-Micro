package com.food_delivery.profile.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatusCode = errorCode.getHttpStatusCode();
    }

    private final HttpStatusCode httpStatusCode;
}
