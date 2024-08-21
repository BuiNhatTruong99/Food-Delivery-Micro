package com.food_delivery.restaurant.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "addon_food")
public class AddonFood extends BaseEntity {
    @Field("restaurant_id")
    private String restaurantId;
    @Field("name")
    private String name;
    @Field("image_url")
    private String imageUrl;
    @Field(value = "price", targetType = FieldType.DECIMAL128)
    private BigDecimal price;
}
