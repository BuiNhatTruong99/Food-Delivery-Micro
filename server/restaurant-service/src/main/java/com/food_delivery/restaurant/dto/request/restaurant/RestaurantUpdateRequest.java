package com.food_delivery.restaurant.dto.request.restaurant;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Shipping fee is required")
    @Min(value = 0, message = "Shipping fee must be greater than or equal to 0")
    private BigDecimal shippingFee;
    @NotNull(message = "Minimum shipping time is required")
    @Min(value = 0, message = "Minimum shipping time must be greater than or equal to 0")
    private Integer minShippingTime;
    @NotNull(message = "Maximum shipping time is required")
    @Min(value = 0, message = "Maximum shipping time must be greater than or equal to 0")
    private Integer maxShippingTime;
    @NotEmpty(message = "tags of restaurant is required")
    private List<String> categoryIds;
}
