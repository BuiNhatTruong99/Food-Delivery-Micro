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

    @Field("average_shipping_time")
    private Double averageShippingTime;

    @Field("is_certified")
    @Builder.Default
    private boolean isCertified = false;

    @Field("total_stars")
    @Builder.Default
    private BigDecimal averageStars = BigDecimal.ZERO;

    @Field("total_reviews")
    @Builder.Default
    private Integer totalReviews = 0;

    @Field("tags")
    private List<String> tags;

    @Field("owner_id")
    private Integer ownerId;

    @Field("is_disabled")
    @Builder.Default
    private boolean isDisabled = false;
}
