package com.food_delivery.notification.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatusCode = errorCode.getHttpStatusCode();
    }

    private final HttpStatusCode httpStatusCode;
}
