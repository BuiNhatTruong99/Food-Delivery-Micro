package com.food_delivery.identity.service;

import com.food_delivery.identity.dto.request.address.AddressCreateRequest;
import com.food_delivery.identity.dto.response.UserResponse;
import com.food_delivery.identity.exception.ErrorCode;
import com.food_delivery.identity.exception.ResourceNotFoundException;
import com.food_delivery.identity.mapper.AddressMapper;
import com.food_delivery.identity.mapper.UserMapper;
import com.food_delivery.identity.repository.AddressRepository;
import com.food_delivery.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserMapper userMapper;

    @Override
    public UserResponse createAddress(AddressCreateRequest addressCreateRequest) {
        log.info(
                "ADDRESS: Start process creating new address for user {}",
                addressCreateRequest.getUserId()
        );
        var user = userRepository
                .findById(addressCreateRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_USER_NOT_FOUND));

        var address = addressMapper.toAddress(addressCreateRequest);
        address.setUser(user);
        addressRepository.save(address);
        log.info(
                "ADDRESS: End process creating new address for user {}",
                addressCreateRequest.getUserId()
        );
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse removeAddress(Integer addressId) {
        log.info(
                "ADDRESS: Start process removing address {}",
                addressId
        );

        var address = addressRepository
                .findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_ADDRESS_NOT_FOUND));
        addressRepository.delete(address);
        log.info(
                "ADDRESS: End process removing address {}",
                addressId
        );
        return userMapper.toUserResponse(address.getUser());
    }
}
