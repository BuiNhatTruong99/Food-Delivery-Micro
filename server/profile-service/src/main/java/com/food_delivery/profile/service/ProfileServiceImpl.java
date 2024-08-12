package com.food_delivery.profile.service;

import com.food_delivery.profile.dto.request.ProfileCreateRequest;
import com.food_delivery.profile.dto.response.ProfileResponse;
import com.food_delivery.profile.entity.Profile;
import com.food_delivery.profile.exception.ErrorCode;
import com.food_delivery.profile.exception.ResourceNotFound;
import com.food_delivery.profile.mapper.ProfileMapper;
import com.food_delivery.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public ProfileResponse createProfile(ProfileCreateRequest profileCreateRequest) {
        log.info("PROFILE: Start creating profile for user: {}", profileCreateRequest.getEmail());
        Profile profile = profileMapper.toProfile(profileCreateRequest);
        profileRepository.save(profile);
        log.info("PROFILE: End creating profile");
        return profileMapper.toProfileResponse(profile);
    }

    @Override
    public ProfileResponse getProfile(Integer profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFound(ErrorCode.ERR_PROFILE_NOT_FOUND));
        return profileMapper.toProfileResponse(profile);
    }
}
