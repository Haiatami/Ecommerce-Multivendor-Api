package com.hoanghai.ecommerce.multivendor.api.services;

import com.hoanghai.ecommerce.multivendor.api.entities.Seller;
import com.hoanghai.ecommerce.multivendor.api.entities.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);

    SellerReport updateSellerReport(SellerReport sellerReport);
}
