package com.hoanghai.ecommerce.multivendor.api.services.impl;

import com.hoanghai.ecommerce.multivendor.api.config.JwtProvider;
import com.hoanghai.ecommerce.multivendor.api.entities.Cart;
import com.hoanghai.ecommerce.multivendor.api.entities.Seller;
import com.hoanghai.ecommerce.multivendor.api.entities.User;
import com.hoanghai.ecommerce.multivendor.api.entities.VerificationCode;
import com.hoanghai.ecommerce.multivendor.api.enums.UserRole;
import com.hoanghai.ecommerce.multivendor.api.repositorys.CartRepository;
import com.hoanghai.ecommerce.multivendor.api.repositorys.SellerRepository;
import com.hoanghai.ecommerce.multivendor.api.repositorys.UserRepository;
import com.hoanghai.ecommerce.multivendor.api.repositorys.VerificationCodeRepository;
import com.hoanghai.ecommerce.multivendor.api.requests.LoginRequest;
import com.hoanghai.ecommerce.multivendor.api.requests.SignupRequest;
import com.hoanghai.ecommerce.multivendor.api.responses.AuthResponse;
import com.hoanghai.ecommerce.multivendor.api.services.AuthService;
import com.hoanghai.ecommerce.multivendor.api.services.EmailService;
import com.hoanghai.ecommerce.multivendor.api.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserServiceImpl;
    private final SellerRepository sellerRepository;

    @Override
    public void sentLoginOtp(String email, UserRole role) throws Exception {
        String SIGNING_PREFIX = "signing_";

        if(email.startsWith(SIGNING_PREFIX)){
            email = email.substring(SIGNING_PREFIX.length());

            if(role.equals(UserRole.SELLER)){
                Seller seller = sellerRepository.findByEmail(email);

                if(seller == null){
                    throw new Exception("Seller not found");
                }
            }else{
                User user = userRepository.findByEmail(email);

                if(user == null){
                    throw new Exception("User not exist with provided email");
                }
            }
        }

        VerificationCode isExist = verificationCodeRepository.findByEmail(email);
        if(isExist != null){
            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);

        verificationCodeRepository.save(verificationCode);

        String subject = "Login / Signup otp";
        String text = "Your login / signup otp is: " + otp;

        emailService.sendVerificationOtpEmail(email, otp, subject, text);
        
    }

    @Override
    public String createUser(SignupRequest req) throws Exception{
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        if(verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("Wrong otp ...");
        }

        User user = userRepository.findByEmail(req.getEmail());

        if(user == null){
            User createdUser = new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(UserRole.CUSTOMER);
            createdUser.setMobile("0909090909");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createdUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRole.CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signing(LoginRequest req) throws Exception {
        String username = req.getEmail();
        String otp = req.getOtp();

        Authentication authentication = authenticate(username, otp);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login success");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        authResponse.setRole(UserRole.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) throws Exception {
        UserDetails userDetails = customUserServiceImpl.loadUserByUsername(username);

        String SELLER_PREFIX = "seller_";

        if(username.startsWith(SELLER_PREFIX)) {
            username = username.substring(SELLER_PREFIX.length());
        }

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        if(verificationCode == null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("Wrong otp ...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
