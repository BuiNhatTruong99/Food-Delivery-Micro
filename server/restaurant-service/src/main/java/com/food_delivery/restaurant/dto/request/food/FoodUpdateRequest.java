package com.food_delivery.restaurant.dto.request.food;

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
public class FoodUpdateRequest {
    @NotBlank(message = "Food Id is required")
    private String id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotEmpty(message = "Image is required")
    private String imageUrl;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Price is required")
    private BigDecimal price;
    @NotBlank(message = "Category is required")
    private String category;
    @NotEmpty(message = "Addon Food Ids are required")
    private List<String> addonFoodIds;
}
