package com.hoanghai.ecommerce.multivendor.api.services;

import com.hoanghai.ecommerce.multivendor.api.enums.UserRole;
import com.hoanghai.ecommerce.multivendor.api.requests.LoginRequest;
import com.hoanghai.ecommerce.multivendor.api.requests.SignupRequest;
import com.hoanghai.ecommerce.multivendor.api.responses.AuthResponse;

public interface AuthService {
    void sentLoginOtp(String email, UserRole role) throws Exception;

    String createUser(SignupRequest req) throws Exception;

    AuthResponse signing(LoginRequest req) throws Exception;
}
