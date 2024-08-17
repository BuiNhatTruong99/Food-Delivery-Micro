package com.food_delivery.restaurant.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "restaurant")
public class Restaurant extends BaseEntity {

    @Field("name")
    private String name;

    @Field("address")
    private String address;

    @Field("image_url")
    private String imageUrl;

    @Field("cover_image_url")
    private String coverImageUrl;

    @Field(value = "shipping_fee", targetType = FieldType.DECIMAL128)
    private BigDecimal shippingFee;

    @Field("shipping_time")
    private String shippingTime;

    @Field("is_certified")
    @Builder.Default
    private boolean isCertified = false;

    @Field("total_stars")
    private Double averageStars;

    @Field("total_reviews")
    private Integer totalReviews;

    @Field("tags")
    @DocumentReference
    private List<Category> tags;

    @Field("owner_id")
    private Integer ownerId;

    @Field("is_disabled")
    @Builder.Default
    private boolean isDisabled = false;
}
