package com.food_delivery.profile.controller;

import com.food_delivery.profile.dto.request.ProfileCreateRequest;
import com.food_delivery.profile.dto.response.ApiResponse;
import com.food_delivery.profile.dto.response.ProfileResponse;
import com.food_delivery.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class InternalProfileController {
    private final ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(
            @Valid
            @RequestBody ProfileCreateRequest profileCreateRequest
    ) {
        var data = profileService.createProfile(profileCreateRequest);
        return ResponseEntity.ok(ApiResponse.<ProfileResponse>builder().data(data).build());
    }
}
