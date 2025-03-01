package com.hoanghai.ecommerce.multivendor.api.requests;

import com.hoanghai.ecommerce.multivendor.api.enums.UserRole;
import lombok.Data;

@Data
public class LoginOtpRequest {
    private String email;

    private String otp;

    private UserRole role;
}
