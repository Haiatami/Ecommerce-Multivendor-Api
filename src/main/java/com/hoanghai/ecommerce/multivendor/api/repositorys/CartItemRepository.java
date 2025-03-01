package com.hoanghai.ecommerce.multivendor.api.repositorys;

import com.hoanghai.ecommerce.multivendor.api.entities.Cart;
import com.hoanghai.ecommerce.multivendor.api.entities.CartItem;
import com.hoanghai.ecommerce.multivendor.api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
