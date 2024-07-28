package com.food_delivery.api_gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food_delivery.api_gateway.dto.response.ApiResponse;
import com.food_delivery.api_gateway.service.IdentityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    public static class Config {

    }

    @Autowired
    private IdentityService identityService;

    public AuthenticationFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.warn("No AuthHeader");
                return unauthenticated(exchange.getResponse());
        }
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
                log.info("AuthHeader: " + authHeader);
            }

            return identityService.introspectToken(authHeader)
                    .flatMap(response -> {
                        if (response.getData().isValid()) {
                            log.info("Authenticated successfully");
                            return chain.filter(exchange);
                        } else {
                            log.error("Failed to authenticate");
                            return unauthenticated(exchange.getResponse());
                        }
                    })
                    .onErrorResume(e -> {
                        log.error("Error: " + e.getMessage());
                        return unauthenticated(exchange.getResponse());
                    });
        };
    }

    Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message("Unauthenticated")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}
