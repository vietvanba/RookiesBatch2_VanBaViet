package com.example.RookieShop.controller;


import com.example.RookieShop.dto.ProductDTO;
import com.example.RookieShop.exception.CanNotConvertException;
import com.example.RookieShop.model.Product;
import com.example.RookieShop.repository.ProductImageRepository;
import com.example.RookieShop.service.ImageUploader;
import com.example.RookieShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@RestControllerAdvice
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService service;

    @Autowired
    ProductImageRepository imageRepository;

    @Autowired
    ImageUploader imageUploader;
    @PostMapping(value = "/products")
    @Transactional
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product,
                                           @RequestParam(name = "categoryID") int categoryID) {
        try {


            service.save(product, categoryID);
            product.getImages().forEach(x -> {
                x.setProduct(product);
                x.setUrl(imageUploader.uploadImage(x.getUrl()));
                imageRepository.save(x);
            });
            return ResponseEntity.ok(new ProductDTO().toDTO(product));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }

    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id) {
        try {
            return ResponseEntity.ok(new ProductDTO().toDTO(service.getByID(id)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }

    }
    @GetMapping("/products/categories")
    public ResponseEntity<?> getProductByCategories(@RequestParam(name = "categoriesID") int id) {
        try {
            return ResponseEntity.ok(new ProductDTO().toListDTO(service.getByCategoriesID(id)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }

    }
    @GetMapping("/products/search/{key}")
    public ResponseEntity<?> search(@PathVariable String key)
    {
        return ResponseEntity.ok(new ProductDTO().toListDTO(service.search(key)));
    }
    @GetMapping("/products")
    public ResponseEntity<?> getAllProduct() {
        try {
            return ResponseEntity.ok(new ProductDTO().toListDTO(service.getAll()));
        } catch (Exception e) {

            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }

    }

    @DeleteMapping("/products")
    public ResponseEntity<?> deleteProduct(@RequestParam(name = "id") int id) {

        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping("/products")
    public ResponseEntity<?> updateProduct(@RequestBody Product product,
                                           @RequestParam(name =
                                                   "id") int id) {

        return ResponseEntity.ok(new ProductDTO().toDTO(service.udpateById(product, id)));
    }
    @PatchMapping("/product/rate/{id}")
    public ResponseEntity<?> rateProduct(@PathVariable int id,@RequestParam(name=
            "idOrderDetail") int idOrderDetail,@RequestParam(name="rate")float rate)
    {
        return ResponseEntity.ok(new ProductDTO().toDTO(service.rate(id,idOrderDetail,
                rate)));
    }

}
