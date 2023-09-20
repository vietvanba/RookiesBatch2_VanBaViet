package com.example.RookieShop.repository;

import com.example.RookieShop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RookieShop.model.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
    @Transactional
    @Modifying
    @Query("SELECT o FROM Order o WHERE o.user.user_id=?1")
    List<Order> findAllOrderByUser(int id);
}
