package com.hoanghai.ecommerce.multivendor.api.services;

import com.hoanghai.ecommerce.multivendor.api.entities.Cart;
import com.hoanghai.ecommerce.multivendor.api.entities.CartItem;
import com.hoanghai.ecommerce.multivendor.api.entities.Product;
import com.hoanghai.ecommerce.multivendor.api.entities.User;

public interface CartService {
    CartItem addCartItem(User user, Product product, String size, int quantity);

    Cart findUserCart(User user);
}
