package com.food_delivery.identity.repository;

import com.food_delivery.identity.entity.VerifyOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyOtpRepository extends JpaRepository<VerifyOtp, Integer> {
}
