package com.food_delivery.restaurant.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "food")
public class Food extends BaseEntity {
    @Field("restaurant_id")
    private String restaurantId;
    @Field("name")
    private String name;
    @Field("image_url")
    private String imageUrl;
    @Field("description")
    private String description;
    @Field(value = "price", targetType = FieldType.DECIMAL128)
    private BigDecimal price;
    @Field("total_stars")
    @Builder.Default
    private BigDecimal averageStars = BigDecimal.ZERO;
    @Field("total_reviews")
    @Builder.Default
    private Integer totalReviews = 0;
    @Field("category")
    private String category;
    @Field("addon_foods")
    private List<AddonFood> addonFoods;
}
