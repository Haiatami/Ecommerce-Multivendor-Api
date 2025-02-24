package com.hoanghai.ecommerce.multivendor.api.repository;

import com.hoanghai.ecommerce.multivendor.api.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller,Long> {
    Seller findByEmail(String email);
}
