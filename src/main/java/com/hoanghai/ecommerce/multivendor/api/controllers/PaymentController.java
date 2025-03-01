package com.hoanghai.ecommerce.multivendor.api.controllers;

import com.hoanghai.ecommerce.multivendor.api.entities.*;
import com.hoanghai.ecommerce.multivendor.api.responses.ApiResponse;
import com.hoanghai.ecommerce.multivendor.api.responses.PaymentLinkResponse;
import com.hoanghai.ecommerce.multivendor.api.services.TransactionService;
import com.hoanghai.ecommerce.multivendor.api.services.SellerReportService;
import com.hoanghai.ecommerce.multivendor.api.services.SellerService;
import com.hoanghai.ecommerce.multivendor.api.services.PaymentService;
import com.hoanghai.ecommerce.multivendor.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final UserService userService;
    private final PaymentService paymentService;
    private final SellerService sellerService;
    private final SellerReportService sellerReportService;
    private final TransactionService transactionService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccessHandler(@PathVariable String paymentId,
                                                             @RequestParam String paymentLinkId,
                                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        PaymentLinkResponse paymentLinkResponse;

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        boolean paymentSuccess = paymentService.ProceedPaymentOrder(paymentOrder, paymentId, paymentLinkId);

        if(paymentSuccess){
            for(Order order : paymentOrder.getOrders()){
                transactionService.createTransaction(order);
                Seller seller = sellerService.getSellerById(order.getSellerId());
                SellerReport report = sellerReportService.getSellerReport(seller);
                report.setTotalOrders(report.getTotalOrders() + 1);
                report.setTotalEarnings(report.getTotalEarnings() + order.getTotalSellingPrice());
                report.setTotalSales(report.getTotalSales() + order.getOrderItems().size());
                sellerReportService.updateSellerReport(report);

            }
        }

        ApiResponse res = new ApiResponse();
        res.setMessage("Payment successfully");

        return  new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
