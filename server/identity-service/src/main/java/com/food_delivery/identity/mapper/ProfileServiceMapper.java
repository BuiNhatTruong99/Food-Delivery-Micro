package com.food_delivery.identity.mapper;

import com.food_delivery.identity.dto.request.GoogleSignInRequest;
import com.food_delivery.identity.dto.request.ProfileCreateRequest;
import com.food_delivery.identity.dto.request.UserSignUpRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileServiceMapper {
    ProfileCreateRequest toProfileCreateRequest(UserSignUpRequest userSignUpRequest);

    ProfileCreateRequest toProfileCreateRequest(GoogleSignInRequest googleSignInRequest);
}
