package com.example.RookieShop.controller;


import com.example.RookieShop.dto.CategoryDTO;
import com.example.RookieShop.exception.CanNotConvertException;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.Category;
import com.example.RookieShop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestControllerAdvice
@CrossOrigin
@RequestMapping("/api")

public class CategoryController {
    @Autowired
    CategoryService service;

        @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
        try {
            CategoryDTO dto = new CategoryDTO().toDTO(service.save(category));
            return ResponseEntity.ok(dto);
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("Can't convert Entity to DTO");
        }
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategory(@PathVariable int id) {
        Category category = service.getByID(id);
        if(category!=null){
            try {
                return ResponseEntity.ok(new CategoryDTO().toDTO(category));
            }catch (NullPointerException e) {
                throw new CanNotConvertException("Can't convert Entity to DTO",e);
            }
        } else {
            throw new NotFoundEntityException("Not found entity with id: " + id);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            return ResponseEntity.ok(new CategoryDTO().toListDTO(service.getAll()));
        } catch (NullPointerException e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }
    }

    @DeleteMapping("/categories")
    public ResponseEntity<?> deleteCategory(@RequestParam(name = "id") int id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping("/categories")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO,
                                            @RequestParam(name =
                                                    "id") int id) {
        return ResponseEntity.ok(new CategoryDTO().toDTO(service.udpateById(categoryDTO, id)));
    }

}
