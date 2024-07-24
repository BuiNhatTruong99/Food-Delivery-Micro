package com.food_delivery.profile.service;

import com.food_delivery.profile.dto.request.ProfileCreateRequest;
import com.food_delivery.profile.dto.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse createProfile(ProfileCreateRequest profileCreateRequest);

    ProfileResponse getProfile(Integer profileId);
}
