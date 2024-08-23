package com.food_delivery.restaurant.entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "category")
public class Category extends BaseEntity {
    @Field("name")
    @Indexed(unique = true)
    private String name;

    @Field("image_url")
    private String imageUrl;
}
