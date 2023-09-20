package com.example.RookieShop.service;

import com.example.RookieShop.dto.ProductCommentDTO;
import com.example.RookieShop.exception.CanNotDeleteEntityException;
import com.example.RookieShop.exception.CanNotSaveEntityException;
import com.example.RookieShop.exception.CanNotUpdateEntityException;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.Product;
import com.example.RookieShop.model.ProductComment;
import com.example.RookieShop.model.User;
import com.example.RookieShop.repository.ProductCommentRepository;
import com.example.RookieShop.repository.ProductRepository;
import com.example.RookieShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
//@Component
public class ProductCommentService {

    @Autowired
    ProductCommentRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;


    public ProductComment save(ProductComment productComment, int user_id, int product_id) {
        User user= userRepository.findById(user_id).orElse(null);
        Product product=  productRepository.findById(product_id).orElse(null);
        if (user==null)
            throw new NotFoundEntityException("Not found user with id: "+user_id);
        if(product==null)
            throw new NotFoundEntityException("Not found product with id: "+product_id);
        try {
            productComment.setUser(user);
            productComment.setProduct(product);
            productComment.setTime(new Timestamp(System.currentTimeMillis()));
            return repository.save(productComment);
        } catch (Exception e) {
            throw new CanNotSaveEntityException("Can't save comment ", e);
        }
    }

    public ProductComment getByID(int id) {
        ProductComment comment = repository.findById(id).orElse(null);
        if (comment != null) {
            return comment;
        } else {
            throw new NotFoundEntityException("Not found comment with id" + id);

        }

    }

    public List<ProductComment> getAll() {
        List<ProductComment> list = repository.findAll();
        if (list.size() != 0) {
            return list;
        } else {
            throw new NotFoundEntityException("Can't get any comment");

        }
    }

    public List<ProductComment> getAllWithProductID(int id) {
        List<ProductComment> list = repository.findAllByProduct(id);
        if (list.size() != 0) {
            return list;
        } else {
            throw new NotFoundEntityException("Can't get any comment with product_id:" + id);

        }

    }

    public ProductComment updateById(ProductCommentDTO productCommentDTO, int id) {
        ProductComment productComment = repository.findById(id).orElse(null);
        if (productComment != null) {
            try {
                if ((!productComment.getContent().equals(productCommentDTO.getContent())) && productComment.getContent() != null)
                    productComment.setContent(productCommentDTO.getContent());
                return repository.save(productComment);

            } catch (Exception e) {
                throw new CanNotUpdateEntityException("Can't update comment with id: " + id
                        , e);
            }
        } else {
            throw new NotFoundEntityException("Not found entity with id: " + id);
        }
    }

    public String deleteById(int id) {
        ProductComment comment = repository.findById(id).orElse(null);
        if (comment != null) {
            try {
                repository.delete(comment);
                return "Delete successful";
            } catch (Exception e) {
                throw new CanNotDeleteEntityException("Can't delete Customer with id " + id, e);
            }
        } else {
            throw new NotFoundEntityException("Not found entity with id: " + id);
        }
    }
}
