package com.food_delivery.profile.controller;

import com.food_delivery.profile.dto.request.ProfileCreateRequest;
import com.food_delivery.profile.dto.response.ApiResponse;
import com.food_delivery.profile.dto.response.ProfileResponse;
import com.food_delivery.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(
            @Valid
            @RequestBody ProfileCreateRequest profileCreateRequest
    ) {
        var data = profileService.createProfile(profileCreateRequest);
        return ResponseEntity.ok(ApiResponse.<ProfileResponse>builder().data(data).build());
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(
            @PathVariable Integer profileId
    ) {
        var data = profileService.getProfile(profileId);
        return ResponseEntity.ok(ApiResponse.<ProfileResponse>builder().data(data).build());
    }
}
