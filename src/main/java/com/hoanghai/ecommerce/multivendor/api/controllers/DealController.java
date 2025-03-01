package com.hoanghai.ecommerce.multivendor.api.controllers;

import com.hoanghai.ecommerce.multivendor.api.entities.Deal;
import com.hoanghai.ecommerce.multivendor.api.responses.ApiResponse;
import com.hoanghai.ecommerce.multivendor.api.services.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
public class DealController {
    private final DealService dealService;

    @PostMapping()
    public ResponseEntity<Deal> createDeal(@RequestBody Deal deal){
        Deal createdDeals = dealService.createDeal(deal);

        return new ResponseEntity<>(createdDeals, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id,
                                           @RequestBody Deal deal) throws Exception {
        Deal updateDeals = dealService.updateDeal(deal, id);

        return ResponseEntity.ok(updateDeals);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDeal(@PathVariable Long id) throws Exception {
        dealService.deleteDeal(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Deal deleted");

        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
}
