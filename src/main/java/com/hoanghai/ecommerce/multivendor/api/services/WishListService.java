package com.hoanghai.ecommerce.multivendor.api.services;

import com.hoanghai.ecommerce.multivendor.api.entities.Product;
import com.hoanghai.ecommerce.multivendor.api.entities.User;
import com.hoanghai.ecommerce.multivendor.api.entities.WishList;

public interface WishListService {
    WishList createWishList(User user);

    WishList getWishListByUserId(User user);

    WishList addProductToWishList(User user, Product product);
}
