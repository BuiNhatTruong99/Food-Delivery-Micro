package com.food_delivery.identity.mapper;

import com.food_delivery.identity.dto.response.TokenRefreshResponse;
import com.food_delivery.identity.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    TokenRefreshResponse toTokenRefreshResponse(UserResponse userResponse);
}
