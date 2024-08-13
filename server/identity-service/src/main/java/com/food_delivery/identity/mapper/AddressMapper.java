package com.food_delivery.identity.mapper;

import com.food_delivery.identity.dto.request.address.AddressCreateRequest;
import com.food_delivery.identity.dto.response.AddressResponse;
import com.food_delivery.identity.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(AddressCreateRequest addressCreateRequest);

    AddressResponse toAddressResponse(Address address);
}
