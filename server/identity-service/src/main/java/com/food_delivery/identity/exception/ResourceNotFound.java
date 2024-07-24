package com.food_delivery.identity.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatusCode = errorCode.getHttpStatusCode();
    }

    private final HttpStatusCode httpStatusCode;
}
