package com.food_delivery.restaurant.dto.request.restaurant;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class RestaurantUpdateRequest {

    @NotBlank(message = "Id of restaurant is required")
    private String id;
    @NotBlank(message = "Name of restaurant is required")
    private String name;
    @NotBlank(message = "Address of restaurant is required")
    private String address;
    @NotEmpty(message = "image of restaurant is required")
    private String imageUrl;
    private String coverImageUrl;
    private BigDecimal shippingFee;
    private String shippingTime;
    @NotEmpty(message = "tags of restaurant is required")
    private List<String> categoryIds;
}
