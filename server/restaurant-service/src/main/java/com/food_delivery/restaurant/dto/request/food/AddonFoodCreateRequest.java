package com.food_delivery.restaurant.dto.request.food;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddonFoodCreateRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotEmpty(message = "Image is required")
    private String imageUrl;
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private BigDecimal price;
    @NotBlank(message = "Restaurant id is required")
    private String restaurantId;
}
