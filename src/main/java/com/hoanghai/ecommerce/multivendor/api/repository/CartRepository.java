package com.hoanghai.ecommerce.multivendor.api.repository;

import com.hoanghai.ecommerce.multivendor.api.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
