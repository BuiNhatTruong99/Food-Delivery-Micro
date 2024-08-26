package com.food_delivery.rating.client;

import com.food_delivery.rating.dto.response.ApiResponse;
import com.food_delivery.rating.dto.response.UserResponse;
import com.food_delivery.rating.security.AuthenticationRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "identity-service",
        configuration = {AuthenticationRequestInterceptor.class}
)
public interface UserService {
    @GetMapping(
            value = "/identity/users/get-user/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ApiResponse<UserResponse> getUser(
            @PathVariable("userId") Integer userId
    );
}
