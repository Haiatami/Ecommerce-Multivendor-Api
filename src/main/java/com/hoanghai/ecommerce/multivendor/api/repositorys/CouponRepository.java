package com.hoanghai.ecommerce.multivendor.api.repositorys;

import com.hoanghai.ecommerce.multivendor.api.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Coupon findByCode(String code);
}
