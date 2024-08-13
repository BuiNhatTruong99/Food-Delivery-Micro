package com.food_delivery.identity.controller;

import com.food_delivery.identity.dto.request.address.AddressCreateRequest;
import com.food_delivery.identity.dto.response.ApiResponse;
import com.food_delivery.identity.dto.response.UserResponse;
import com.food_delivery.identity.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserResponse>> createAddress(
            @Valid
            @RequestBody AddressCreateRequest addressCreateRequest
    ) {
        var data = addressService.createAddress(addressCreateRequest);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder().data(data).build());
    }

    @DeleteMapping("/remove/{addressId}")
    public ResponseEntity<ApiResponse<UserResponse>> removeAddress(
            @Valid
            @PathVariable("addressId") Integer addressId
    ) {
        var data = addressService.removeAddress(addressId);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder().data(data).build());
    }
}
