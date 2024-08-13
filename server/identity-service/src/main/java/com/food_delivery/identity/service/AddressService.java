package com.food_delivery.identity.service;

import com.food_delivery.identity.dto.request.address.AddressCreateRequest;
import com.food_delivery.identity.dto.response.UserResponse;

public interface AddressService {
    UserResponse createAddress(AddressCreateRequest addressCreateRequest);

    UserResponse removeAddress(Integer addressId);
}
