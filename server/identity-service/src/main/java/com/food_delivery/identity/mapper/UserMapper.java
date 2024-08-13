package com.food_delivery.identity.mapper;

import com.food_delivery.identity.dto.request.authentication.UserSignUpRequest;
import com.food_delivery.identity.dto.response.UserResponse;
import com.food_delivery.identity.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserSignUpRequest userSignUpRequest);

    UserResponse toUserResponse(User user);
}
