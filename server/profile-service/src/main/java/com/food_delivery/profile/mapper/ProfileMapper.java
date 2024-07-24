package com.food_delivery.profile.mapper;

import com.food_delivery.profile.dto.request.ProfileCreateRequest;
import com.food_delivery.profile.dto.response.ProfileResponse;
import com.food_delivery.profile.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toProfile(ProfileCreateRequest profileCreateRequest);

    ProfileResponse toProfileResponse(Profile profile);
}
