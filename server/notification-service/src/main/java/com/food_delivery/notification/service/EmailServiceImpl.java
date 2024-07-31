package com.food_delivery.notification.service;

import com.food_delivery.notification.client.EmailClient;
import com.food_delivery.notification.dto.request.BodyParam;
import com.food_delivery.notification.dto.request.EmailClientRequest;
import com.food_delivery.notification.dto.request.SendEmailRequest;
import com.food_delivery.notification.dto.response.EmailResponse;
import com.food_delivery.notification.exception.AppException;
import com.food_delivery.notification.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailClient emailClient;

    @Value("${email-service.apiKey}")
    String apiKey;

    @Value("${email-service.senderName}")
    String senderName;

    @Value("${email-service.senderEmail}")
    String senderEmail;

    @Override
    public EmailResponse sendEmail(SendEmailRequest emailRequest) {
        EmailClientRequest emailClientRequest = EmailClientRequest.builder()
                .sender(
                        BodyParam.builder()
                                .email(senderEmail)
                                .name(senderName)
                                .build()
                )
                .to(List.of(emailRequest.getTo()))
                .subject(emailRequest.getSubject())
                .htmlContent(emailRequest.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailClientRequest);
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERR_CANNOT_SEND_EMAIL);
        }
    }
}
