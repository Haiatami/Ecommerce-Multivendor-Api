package com.hoanghai.ecommerce.multivendor.api.controllers;

import com.hoanghai.ecommerce.multivendor.api.services.SellerService;
import com.hoanghai.ecommerce.multivendor.api.entities.Seller;
import com.hoanghai.ecommerce.multivendor.api.enums.AccountStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {
    private final SellerService sellerService;

    @PatchMapping("/seller/{id}/status/{status}")
    public ResponseEntity<Seller> updateSellerStatus(@PathVariable Long id,
                                                     @PathVariable AccountStatus status) throws Exception {
        Seller updatedSeller = sellerService.updateSellerAccountStatus(id, status);

        return ResponseEntity.ok(updatedSeller);
    }
}