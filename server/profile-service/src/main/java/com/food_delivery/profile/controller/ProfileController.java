package com.food_delivery.profile.controller;

import com.food_delivery.profile.dto.response.ApiResponse;
import com.food_delivery.profile.dto.response.ProfileResponse;
import com.food_delivery.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/get/{profileId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(
            @PathVariable Integer profileId
    ) {
        var data = profileService.getProfile(profileId);
        return ResponseEntity.ok(ApiResponse.<ProfileResponse>builder().data(data).build());
    }

}
