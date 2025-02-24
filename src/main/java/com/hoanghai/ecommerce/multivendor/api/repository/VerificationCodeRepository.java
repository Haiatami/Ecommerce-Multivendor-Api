package com.hoanghai.ecommerce.multivendor.api.repository;

import com.hoanghai.ecommerce.multivendor.api.entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {
    VerificationCode findByEmail(String email);
}
