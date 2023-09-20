package com.example.RookieShop.repository;

import com.example.RookieShop.model.Customer;
import com.example.RookieShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Transactional
    @Modifying
    @Query("SELECT c FROM Customer c WHERE c.user.user_id=?1")
    Optional<Customer> findByUserId(int id);
}
