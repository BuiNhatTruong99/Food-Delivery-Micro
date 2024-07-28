package com.food_delivery.api_gateway.service;

import com.food_delivery.api_gateway.client.IdentityClient;
import com.food_delivery.api_gateway.dto.request.IntrospectTokenRequest;
import com.food_delivery.api_gateway.dto.response.ApiResponse;
import com.food_delivery.api_gateway.dto.response.IntrospectTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IdentityService {

    private final IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectTokenResponse>> introspectToken(String token) {
        return identityClient.introspectToken(
                IntrospectTokenRequest.builder()
                        .token(token)
                        .build()
        );
    }
}
