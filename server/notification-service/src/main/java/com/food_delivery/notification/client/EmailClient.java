package com.food_delivery.notification.client;

import com.food_delivery.notification.dto.request.EmailClientRequest;
import com.food_delivery.notification.dto.response.EmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "email-service", url = "${email-service.url}")
public interface EmailClient {
    @PostMapping(value = "/v3/smtp/email", produces = MediaType.APPLICATION_JSON_VALUE)
    EmailResponse sendEmail(
            @RequestHeader("api-key") String apiKey,
            @RequestBody EmailClientRequest request);
}
