package com.example.RookieShop.repository;

import com.example.RookieShop.model.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCommentRepository extends JpaRepository<ProductComment, Integer>{
    @Query("Select c FROM ProductComment c WHERE c.product.product_id=?1")
    List<ProductComment> findAllByProduct(int id);
}
