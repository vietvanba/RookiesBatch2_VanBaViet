package com.example.RookieShop.controller;


import com.example.RookieShop.dto.ProductCommentDTO;
import com.example.RookieShop.exception.CanNotConvertException;
import com.example.RookieShop.model.ProductComment;
import com.example.RookieShop.service.ProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@RestControllerAdvice
@CrossOrigin
@RequestMapping("/api")
public class ProductCommentController {
    @Autowired
    ProductCommentService service;

    @PostMapping("/comments")
    public ResponseEntity<?> createProductComment(@Valid @RequestBody ProductComment productComment,
                                                  @RequestParam(name = "userID") int userID,
                                                  @RequestParam(name = "productID") int productID) {
        try {
            productComment.setTime(new Timestamp(System.currentTimeMillis()));
            return ResponseEntity.ok(new ProductCommentDTO().toDTO(service.save(productComment, userID, productID)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<?> getProductComment(@PathVariable int id) {
        try {
            return ResponseEntity.ok(new ProductCommentDTO().toDTO(service.getByID(id)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<?> getAllProductCommentByProduct(@RequestParam(name=
            "idProduct") int idProduct) {
        try {
            return ResponseEntity.ok(new ProductCommentDTO().toListDTO(service.getAllWithProductID(idProduct)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);

        }
    }

    @DeleteMapping("/comments")
    public ResponseEntity<?> deleteProductComment(@RequestParam(name = "id") int id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping("/comments")
    public ResponseEntity<?> updateProductComment(@RequestBody ProductCommentDTO ProductCommentDTO
            , @RequestParam(name = "id") int id) {


        return ResponseEntity.ok(new ProductCommentDTO().toDTO(service.updateById(ProductCommentDTO, id)));
    }

}
