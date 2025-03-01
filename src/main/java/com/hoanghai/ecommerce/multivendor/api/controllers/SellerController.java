package com.hoanghai.ecommerce.multivendor.api.controllers;

import com.hoanghai.ecommerce.multivendor.api.entities.Seller;
import com.hoanghai.ecommerce.multivendor.api.entities.SellerReport;
import com.hoanghai.ecommerce.multivendor.api.entities.VerificationCode;
import com.hoanghai.ecommerce.multivendor.api.enums.AccountStatus;
import com.hoanghai.ecommerce.multivendor.api.exceptions.SellerException;
import com.hoanghai.ecommerce.multivendor.api.requests.LoginRequest;
import com.hoanghai.ecommerce.multivendor.api.responses.AuthResponse;
import com.hoanghai.ecommerce.multivendor.api.services.SellerReportService;
import com.hoanghai.ecommerce.multivendor.api.services.EmailService;
import com.hoanghai.ecommerce.multivendor.api.services.AuthService;
import com.hoanghai.ecommerce.multivendor.api.services.SellerService;
import com.hoanghai.ecommerce.multivendor.api.config.JwtProvider;
import com.hoanghai.ecommerce.multivendor.api.repositorys.VerificationCodeRepository;
import com.hoanghai.ecommerce.multivendor.api.utils.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;
    private final SellerReportService sellerReportService;

//    @PostMapping("/sent/Login-otp")
//    public ResponseEntity<ApiResponse> sentOtpHandler(
//            @RequestBody VerificationCode req) throws Exception {
//        authService.sentLoginOtp(req.getEmail());
//
//        ApiResponse res = new ApiResponse();
//
//        res.setMessage("Otp sent successfully");
//
//        return ResponseEntity.ok(res);
//    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(@RequestBody LoginRequest req) throws Exception {
        String otp = req.getOtp();
        String email = req.getEmail();

        req.setEmail("seller_" + email);

        AuthResponse authResponse = authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verity/{otp}")
    public ResponseEntity<Seller> veritySellerEmail(@PathVariable String otp) throws Exception {
        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if(verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception("Wrong otp ...");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);

        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception, MessagingException {
        Seller savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject = "Email Verification Code";
        String text = "Welcome to Ecommerce, verify your account using this link";
        String frontend_url = "http://localhost:3000/verify-seller/";

        emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject,text + frontend_url);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {
        Seller seller = sellerService.getSellerById(id);

        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);

        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);
        SellerReport report = sellerReportService.getSellerReport(seller);

        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Seller>> getAllSellers(@RequestParam(required = false) AccountStatus status){
        List<Seller> sellers = sellerService.getAllSellers(status);

        return ResponseEntity.ok(sellers);
    }

    @PatchMapping()
    public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt,
                                               @RequestBody Seller seller) throws Exception {
        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Seller> deleteSeller(@PathVariable Long id) throws Exception {
        sellerService.deleteSeller(id);

        return ResponseEntity.noContent().build();
    }
}
