package com.hoanghai.ecommerce.multivendor.api.services;

import com.hoanghai.ecommerce.multivendor.api.entities.Order;
import com.hoanghai.ecommerce.multivendor.api.entities.PaymentOrder;
import com.hoanghai.ecommerce.multivendor.api.entities.User;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

import java.util.Set;

public interface PaymentService {
    PaymentOrder createOrder(User user, Set<Order> orders);

    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception;

    Boolean ProceedPaymentOrder(PaymentOrder paymentOrder,
                                String paymentId,
                                String paymentLinkId) throws RazorpayException;

    PaymentLink createRazorpayPaymentLink(User user, Long amount, Long orderId) throws RazorpayException;

    String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;
}
