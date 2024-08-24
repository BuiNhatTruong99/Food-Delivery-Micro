package com.food_delivery.rating.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "rating")
public class Rating extends BaseEntity {
    private String restaurantId;
    private String foodId;
    private Integer userId;
    private String username;
    private String avatarUrl;
    private int ratingStar;
    private String comment;
}
