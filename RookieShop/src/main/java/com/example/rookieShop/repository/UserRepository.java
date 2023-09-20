package com.example.RookieShop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.RookieShop.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String user_name);

    Boolean existsByEmail(String email);
}
