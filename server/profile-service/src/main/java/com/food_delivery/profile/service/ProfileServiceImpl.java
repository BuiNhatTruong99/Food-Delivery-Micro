package com.food_delivery.profile.service;

import com.food_delivery.profile.dto.request.ProfileCreateRequest;
import com.food_delivery.profile.dto.response.ProfileResponse;
import com.food_delivery.profile.entity.Profile;
import com.food_delivery.profile.exception.ErrorCode;
import com.food_delivery.profile.exception.ResourceNotFound;
import com.food_delivery.profile.mapper.ProfileMapper;
import com.food_delivery.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public ProfileResponse createProfile(ProfileCreateRequest profileCreateRequest) {
        Profile profile = profileMapper.toProfile(profileCreateRequest);
        profileRepository.save(profile);
        return profileMapper.toProfileResponse(profile);
    }

    @Override
    public ProfileResponse getProfile(Integer profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFound(ErrorCode.ERR_PROFILE_NOT_FOUND));
        return profileMapper.toProfileResponse(profile);
    }
}
