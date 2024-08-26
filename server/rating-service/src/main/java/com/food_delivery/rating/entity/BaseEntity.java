package com.food_delivery.rating.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class BaseEntity {

    @Id
    private String id;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
