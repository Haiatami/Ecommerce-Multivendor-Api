package com.hoanghai.ecommerce.multivendor.api.controllers;

import com.hoanghai.ecommerce.multivendor.api.entities.User;
import com.hoanghai.ecommerce.multivendor.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> userDetailHandler(
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        return ResponseEntity.ok(user);
    }

}
