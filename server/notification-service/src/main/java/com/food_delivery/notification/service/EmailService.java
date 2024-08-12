package com.food_delivery.notification.service;

import com.food_delivery.notification.dto.request.SendEmailRequest;
import com.food_delivery.notification.dto.response.EmailResponse;

public interface EmailService {
    EmailResponse sendEmail(SendEmailRequest emailRequest);

    EmailResponse sendEmailResetPassword(SendEmailRequest emailRequest);

}
