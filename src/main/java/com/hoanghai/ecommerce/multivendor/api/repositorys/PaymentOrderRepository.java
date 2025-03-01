package com.hoanghai.ecommerce.multivendor.api.repositorys;

import com.hoanghai.ecommerce.multivendor.api.entities.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Long> {
    PaymentOrder findByPaymentLinkId(String paymentId);
}
