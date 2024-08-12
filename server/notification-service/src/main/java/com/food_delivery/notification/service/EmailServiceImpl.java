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

    @Value("${email-service.clientUrl}")
    String clientUrl;

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
                .htmlContent(templateBodyEmailOTP(emailRequest.getHtmlContent()))
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailClientRequest);
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERR_CANNOT_SEND_EMAIL);
        }
    }

    @Override
    public EmailResponse sendEmailResetPassword(SendEmailRequest emailRequest) {
        EmailClientRequest emailClientRequest = EmailClientRequest.builder()
                .sender(
                        BodyParam.builder()
                                .email(senderEmail)
                                .name(senderName)
                                .build()
                )
                .to(List.of(emailRequest.getTo()))
                .subject(emailRequest.getSubject())
                .htmlContent(templateBodyEmailResetPassword(emailRequest.getHtmlContent(), emailRequest.getTo()))
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailClientRequest);
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERR_CANNOT_SEND_EMAIL);
        }
    }

    private String templateBodyEmailOTP(String otp) {
        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; }" +
                "h2 { color: #4CAF50; }" +
                "p { font-size: 16px; }" +
                "strong { color: #FF5722; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h2>Welcome to Food Delivery!</h2>" +
                "<p>Dear Customer,</p>" +
                "<p>Thank you for joining our platform. Your verification code is <strong>" + otp + "</strong>.</p>" +
                "<p>This code will expire in 15 minutes.</p>" +
                "<p>Best regards,<br>Food Delivery Team</p>" +
                "</body>" +
                "</html>";
    }

    private String templateBodyEmailResetPassword(String otp, BodyParam param) {
        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; }" +
                "h2 { color: #4CAF50; }" +
                "p { font-size: 16px; }" +
                "strong { color: #FF5722; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h2>Welcome to Food Delivery!</h2>" +
                "<p>Dear Customer,</p>" +
                "<p>Thank you for joining our platform. Please click on the following link to reset your password: " +
                "<strong><a href=\"" + clientUrl + "/change-password?email=" + param.getEmail() +
                "&otp=" + otp + "\">" + "Link" +
                "</a></strong>.</p>" +
                "<p>This link will expire in 15 minutes.</p>" +
                "<p>Best regards,<br>Food Delivery Team</p>" +
                "</body>" +
                "</html>";
    }
}
