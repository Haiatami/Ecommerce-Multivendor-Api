package com.hoanghai.ecommerce.multivendor.api.repositorys;

import com.hoanghai.ecommerce.multivendor.api.entities.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerReportRepository extends JpaRepository<SellerReport,Long> {
    SellerReport findBySellerId(Long sellerId);
}
