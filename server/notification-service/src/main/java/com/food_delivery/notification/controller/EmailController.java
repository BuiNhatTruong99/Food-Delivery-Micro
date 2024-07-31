package com.food_delivery.notification.controller;

import com.food_delivery.notification.dto.request.SendEmailRequest;
import com.food_delivery.notification.dto.response.ApiResponse;
import com.food_delivery.notification.dto.response.EmailResponse;
import com.food_delivery.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/email/send")
    public ResponseEntity<ApiResponse<EmailResponse>> sendEmail(
            @RequestBody SendEmailRequest emailRequest
    ) {
        var data = emailService.sendEmail(emailRequest);
        return ResponseEntity.ok(ApiResponse.<EmailResponse>builder().data(data).build());
    }
}
