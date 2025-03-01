package com.hoanghai.ecommerce.multivendor.api.services;

import com.hoanghai.ecommerce.multivendor.api.entities.Product;
import com.hoanghai.ecommerce.multivendor.api.entities.Seller;
import com.hoanghai.ecommerce.multivendor.api.exceptions.ProductException;
import com.hoanghai.ecommerce.multivendor.api.requests.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductRequest req, Seller seller);

    void deleteProduct(Long productId) throws ProductException;

    Product updateProduct(Long productId, Product product) throws ProductException;

    Product findProductById(Long productId) throws ProductException;

    List<Product> searchProducts(String query);

    Page<Product> getAllProducts(String category, String brand, String color,
                                 String size, Integer minPrice, Integer maxPrice,
                                 Integer minDiscount, String sort, String stock, Integer pageNumber);

    List<Product> getProductBySellerId(Long sellerId);
}
