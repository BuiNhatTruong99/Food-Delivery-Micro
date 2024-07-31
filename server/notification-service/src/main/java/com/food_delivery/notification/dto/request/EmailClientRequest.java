package com.food_delivery.notification.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailClientRequest {
    BodyParam sender;
    List<BodyParam> to;
    String subject;
    String htmlContent;
}
