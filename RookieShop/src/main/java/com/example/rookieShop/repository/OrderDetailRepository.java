package com.example.RookieShop.repository;

import com.example.RookieShop.model.OrderDetail;
import com.example.RookieShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
    Boolean existsByProduct(Product product);
}
