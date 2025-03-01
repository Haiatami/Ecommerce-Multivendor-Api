package com.hoanghai.ecommerce.multivendor.api.services;

import com.hoanghai.ecommerce.multivendor.api.entities.User;

public interface UserService {
    User findUserByJwtToken(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
