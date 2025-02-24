package com.hoanghai.ecommerce.multivendor.api.repository;

import com.hoanghai.ecommerce.multivendor.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
