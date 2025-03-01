package com.hoanghai.ecommerce.multivendor.api.services.impl;

import com.hoanghai.ecommerce.multivendor.api.entities.Product;
import com.hoanghai.ecommerce.multivendor.api.entities.Review;
import com.hoanghai.ecommerce.multivendor.api.entities.User;
import com.hoanghai.ecommerce.multivendor.api.repositorys.ReviewRepository;
import com.hoanghai.ecommerce.multivendor.api.requests.CreateReviewRequest;
import com.hoanghai.ecommerce.multivendor.api.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(CreateReviewRequest req, User user, Product product) {
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReviewText(req.getReviewText());
        review.setRating(req.getReviewRating());
        review.setProductImages(req.getProductImages());

        product.getReviews().add(review);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception {
        Review review = getReviewById(reviewId);

        if(review.getUser().getId().equals(userId)){
            review.setReviewText(reviewText);
            review.setRating(rating);
            return reviewRepository.save(review);
        }

        throw new Exception("You can't update this review");
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {
        Review review = getReviewById(reviewId);

        if(review.getUser().getId().equals(userId)){
            throw new Exception("You can't delete this review");
        }

        reviewRepository.delete(review);
    }

    @Override
    public Review getReviewById(Long reviewId) throws Exception {
        return reviewRepository.findById(reviewId).orElseThrow(() ->
                new Exception("Review not found"));
    }
}
