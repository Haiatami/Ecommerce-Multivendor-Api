package com.hoanghai.ecommerce.multivendor.api.responses;

import com.hoanghai.ecommerce.multivendor.api.enums.UserRole;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserRole role;
}
