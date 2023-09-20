package com.example.RookieShop.repository;

import com.example.RookieShop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RookieShop.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    @Transactional
    @Modifying
    @Query("SELECT p FROM Product p WHERE lower(p.product_name)like %?1%")
    Optional<List<Product>> findListProductByKey(String key);
    @Transactional
    @Modifying
    @Query("SELECT p FROM Product p WHERE p.product_categories.product_categories_id=?1")
    List<Product> findByCategoriesId(int id);
}
