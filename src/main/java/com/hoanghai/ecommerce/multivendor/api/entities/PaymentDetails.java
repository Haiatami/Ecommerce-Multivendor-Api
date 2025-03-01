package com.hoanghai.ecommerce.multivendor.api.entities;

import com.hoanghai.ecommerce.multivendor.api.enums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentDetails {
    private String paymentId;

    private String razorpayPaymentLinkId;

    private String razorpayPaymentLinkReferenceId;

    private String razorpayPaymentLinkStatus;

    private String razorpayPaymentId;

    private PaymentStatus status;
}
