package com.example.RookieShop.controller;


import com.example.RookieShop.dto.UserDTO;
import com.example.RookieShop.exception.CanNotConvertException;
import com.example.RookieShop.model.User;
import com.example.RookieShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestControllerAdvice
@CrossOrigin
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {

        try {
            return ResponseEntity.ok(new UserDTO().toDTO(service.save(user)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }
    }

    @PostMapping("/admin/users")
    public ResponseEntity<?> createAdminUser(@RequestBody User user) {

        try {
            return ResponseEntity.ok(new UserDTO().toDTO(service.saveAdmin(user)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {

        try {
            return ResponseEntity.ok(new UserDTO().toDTO(service.getByID(id)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser() {
        try {
            return ResponseEntity.ok(new UserDTO().toListDTO(service.getAll()));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }

    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "id") int id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping("/users")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, @RequestParam(name
            = "id") int id) {

        return ResponseEntity.ok(new UserDTO().toDTO(service.udpateById(userDTO, id)));
    }

}
