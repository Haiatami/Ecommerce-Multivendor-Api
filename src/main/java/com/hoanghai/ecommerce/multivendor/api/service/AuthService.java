package com.hoanghai.ecommerce.multivendor.api.service;

import com.hoanghai.ecommerce.multivendor.api.request.LoginRequest;
import com.hoanghai.ecommerce.multivendor.api.request.SignupRequest;
import com.hoanghai.ecommerce.multivendor.api.responses.AuthResponse;

public interface AuthService {
    void sentLoginOtp(String email) throws Exception;

    String createUser(SignupRequest req) throws Exception;

    AuthResponse signing(LoginRequest req);
}
