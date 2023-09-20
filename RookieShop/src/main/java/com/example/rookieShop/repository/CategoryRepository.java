package com.example.RookieShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RookieShop.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
