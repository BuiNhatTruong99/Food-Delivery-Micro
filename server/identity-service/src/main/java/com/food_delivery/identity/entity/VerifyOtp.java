package com.food_delivery.identity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table(name = "verify_otp")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VerifyOtp extends BaseEntity {
    @Column(name = "otp")
    private Integer otp;
    @Column(name = "expiration_time")
    private Date expirationTime;
    @ManyToOne
    private User user;
}
