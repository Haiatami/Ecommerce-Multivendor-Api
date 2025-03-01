package com.hoanghai.ecommerce.multivendor.api.requests;

import lombok.Data;

import java.util.List;

@Data
public class CreateReviewRequest {
    private String reviewText;

    private double reviewRating;

    private List<String> productImages;
}
