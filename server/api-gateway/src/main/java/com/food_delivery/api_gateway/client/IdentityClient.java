package com.food_delivery.api_gateway.client;

import com.food_delivery.api_gateway.dto.request.IntrospectTokenRequest;
import com.food_delivery.api_gateway.dto.response.ApiResponse;
import com.food_delivery.api_gateway.dto.response.IntrospectTokenResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {
    @PostExchange(url = "/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectTokenResponse>> introspectToken(@RequestBody IntrospectTokenRequest request);
}
