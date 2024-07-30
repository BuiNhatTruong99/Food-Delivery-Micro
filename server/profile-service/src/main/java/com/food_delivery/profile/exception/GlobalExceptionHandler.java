package com.food_delivery.profile.exception;

import com.food_delivery.profile.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
        ErrorCode errorCode = ErrorCode.ERR_INTERNAL_SERVER_ERROR;
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getHttpStatusCode().value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, errorCode.getHttpStatusCode());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(400)
                .message(message)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.ERR_UNAUTHORIZED;
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getHttpStatusCode().value())
                .message(errorCode.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, errorCode.getHttpStatusCode());
    }

    @ExceptionHandler(value = ResourceNotFound.class)
    public ResponseEntity<ApiResponse<?>> handleException(ResourceNotFound exception) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(exception.getHttpStatusCode().value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, exception.getHttpStatusCode());
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<?>> handleException(AppException exception) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(exception.getHttpStatusCode().value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, exception.getHttpStatusCode());
    }
}
