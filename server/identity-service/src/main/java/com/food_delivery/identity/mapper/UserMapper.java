package com.food_delivery.identity.mapper;

import com.food_delivery.identity.dto.request.GoogleSignInRequest;
import com.food_delivery.identity.dto.request.UserSignUpRequest;
import com.food_delivery.identity.dto.response.UserResponse;
import com.food_delivery.identity.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserSignUpRequest userSignUpRequest);
    User toUser(GoogleSignInRequest googleSignInRequest);

    UserResponse toUserResponse(User user);
}
