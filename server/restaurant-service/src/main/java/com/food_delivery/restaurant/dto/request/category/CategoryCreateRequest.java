package com.food_delivery.restaurant.dto.request.category;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateRequest {

    @NotBlank(message = "Category name is required")
    private String name;

    @NotEmpty(message = "Category image url is required")
    private String imageUrl;
}
