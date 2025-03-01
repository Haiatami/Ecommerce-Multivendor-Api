package com.hoanghai.ecommerce.multivendor.api.repositorys;

import com.hoanghai.ecommerce.multivendor.api.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByProductId(Long productId);
}
