package com.food_delivery.notification.controller;

import com.food_delivery.notification.dto.request.BodyParam;
import com.food_delivery.notification.dto.request.SendEmailRequest;
import com.food_delivery.kafka.NotificationOtp;
import com.food_delivery.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationController {
    private final EmailService emailService;

    @KafkaListener(topics = "notification-otp")
    public void listenNotificationOtp(NotificationOtp notificationOtp) {
        log.info("Received notification: {}", notificationOtp);
        emailService.sendEmail(SendEmailRequest.builder()
                .to(BodyParam.builder()
                        .email(notificationOtp.getRecipient())
                        .build())
                .subject(notificationOtp.getSubject())
                .htmlContent(notificationOtp.getBody())
                .build());
    }
}
