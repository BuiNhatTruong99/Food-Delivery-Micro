package com.food_delivery.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantResponse {
    private String id;
    private String name;
    private String address;
    private String imageUrl;
    private String coverImageUrl;
    private BigDecimal shippingFee;
    private String shippingTime;
    private boolean isCertified;
    private Double averageStars;
    private Integer totalReviews;
    private List<CategoryResponse> tags;
    private Integer ownerId;
}
