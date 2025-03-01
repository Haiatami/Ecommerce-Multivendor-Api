package com.hoanghai.ecommerce.multivendor.api.controllers;

import com.hoanghai.ecommerce.multivendor.api.entities.Product;
import com.hoanghai.ecommerce.multivendor.api.entities.User;
import com.hoanghai.ecommerce.multivendor.api.entities.WishList;
import com.hoanghai.ecommerce.multivendor.api.services.ProductService;
import com.hoanghai.ecommerce.multivendor.api.services.WishListService;
import com.hoanghai.ecommerce.multivendor.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlists")
@RequiredArgsConstructor
public class WishListController {
    private final UserService userService;
    private final WishListService wishListService;
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<WishList> getWishListByUserId(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        WishList wishList = wishListService.getWishListByUserId(user);

        return ResponseEntity.ok(wishList);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<WishList> addProductToWishList(@PathVariable Long productId,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        Product product = productService.findProductById(productId);

        User user = userService.findUserByJwtToken(jwt);

        WishList updatedWishList = wishListService.addProductToWishList(user, product);

        return ResponseEntity.ok(updatedWishList);
    }
}
