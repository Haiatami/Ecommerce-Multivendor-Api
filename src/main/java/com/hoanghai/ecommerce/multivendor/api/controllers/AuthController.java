package com.hoanghai.ecommerce.multivendor.api.controllers;

import com.hoanghai.ecommerce.multivendor.api.entities.VerificationCode;
import com.hoanghai.ecommerce.multivendor.api.enums.UserRole;
import com.hoanghai.ecommerce.multivendor.api.repository.UserRepository;
import com.hoanghai.ecommerce.multivendor.api.request.SignupRequest;
import com.hoanghai.ecommerce.multivendor.api.responses.ApiResponse;
import com.hoanghai.ecommerce.multivendor.api.responses.AuthResponse;
import com.hoanghai.ecommerce.multivendor.api.service.AuthService;
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

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(
            @RequestBody VerificationCode req) throws Exception {
        authService.sentLoginOtp(req.getEmail());

        ApiResponse res = new ApiResponse();
        res.setMessage("otp sent successfully");

        return ResponseEntity.ok(res);
    }
}
