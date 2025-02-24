package com.hoanghai.ecommerce.multivendor.api.request;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String fullName;
    private String otp;
}
