package com.example.RookieShop.controller;


import com.example.RookieShop.dto.RoleDTO;
import com.example.RookieShop.dto.UserDTO;
import com.example.RookieShop.exception.CanNotConvertException;
import com.example.RookieShop.model.Role;
import com.example.RookieShop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestControllerAdvice
@CrossOrigin
@RequestMapping("/api/public")
public class RoleController {
    @Autowired
    RoleService service;


    @PostMapping("/roles")
    public ResponseEntity<?> createRole(@Valid @RequestBody Role role) {
        try {
            return ResponseEntity.ok(new RoleDTO().toDTO(service.save(role)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<?> getRole(@PathVariable int id) {

        try {
            return ResponseEntity.ok(new RoleDTO().toDTO(service.getByID(id)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }

    }

    @GetMapping("/roles")
    public ResponseEntity<?> getAllRole() {

        try {
            return ResponseEntity.ok(new RoleDTO().toListDTO(service.getAll()));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert Entity to DTO",e);
        }
    }

    @DeleteMapping("/roles")
    public ResponseEntity<?> deleteRole(@RequestParam(name = "id") int id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping("/roles")
    public ResponseEntity<?> updateRole(@RequestBody RoleDTO roleDTO, @RequestParam(name = "id") int id) {

        return ResponseEntity.ok(new RoleDTO().toDTO(service.udpateById(roleDTO, id)));
    }

}
