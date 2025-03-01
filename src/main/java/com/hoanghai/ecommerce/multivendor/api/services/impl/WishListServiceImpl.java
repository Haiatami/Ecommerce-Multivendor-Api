package com.hoanghai.ecommerce.multivendor.api.services.impl;

import com.hoanghai.ecommerce.multivendor.api.entities.Product;
import com.hoanghai.ecommerce.multivendor.api.entities.User;
import com.hoanghai.ecommerce.multivendor.api.entities.WishList;
import com.hoanghai.ecommerce.multivendor.api.repositorys.WishListRepository;
import com.hoanghai.ecommerce.multivendor.api.services.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final WishListRepository wishListRepository;

    @Override
    public WishList createWishList(User user) {
        WishList wishList = new WishList();
        wishList.setUser(user);
        return wishListRepository.save(wishList);
    }

    @Override
    public WishList getWishListByUserId(User user) {
        WishList wishList = wishListRepository.findByUserId(user.getId());

        if(wishList == null){
            wishList = createWishList(user);
        }

        return wishList;
    }

    @Override
    public WishList addProductToWishList(User user, Product product) {
        WishList wishList = getWishListByUserId(user);

        if(wishList.getProducts().contains(product)){
            wishList.getProducts().remove(product);
        }else{
            wishList.getProducts().add(product);
        }

        return wishListRepository.save(wishList);
    }
}
