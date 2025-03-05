package com.hoanghai.ecommerce.multivendor.api.controllers;

import com.hoanghai.ecommerce.multivendor.api.requests.LoginRequest;
import com.hoanghai.ecommerce.multivendor.api.requests.LoginOtpRequest;
import com.hoanghai.ecommerce.multivendor.api.enums.UserRole;
import com.hoanghai.ecommerce.multivendor.api.requests.SignupRequest;
import com.hoanghai.ecommerce.multivendor.api.responses.ApiResponse;
import com.hoanghai.ecommerce.multivendor.api.responses.AuthResponse;
import com.hoanghai.ecommerce.multivendor.api.services.AuthService;
import com.hoanghai.ecommerce.multivendor.api.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {
        String jwt = authService.createUser(req);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("register success");
        res.setRole(UserRole.CUSTOMER);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/sent/Login-signup-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(
            @RequestBody LoginOtpRequest req) throws Exception {
        authService.sentLoginOtp(req.getEmail(), req.getRole());

        ApiResponse res = new ApiResponse();

        res.setMessage("Otp sent successfully");

        return ResponseEntity.ok(res);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginHandler(
            @RequestBody LoginRequest req) throws Exception {
        AuthResponse authResponse= authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }
}
