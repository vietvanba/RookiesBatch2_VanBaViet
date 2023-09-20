package com.example.RookieShop.controller;


import com.example.RookieShop.dto.CustomerDTO;
import com.example.RookieShop.dto.UserDTO;
import com.example.RookieShop.exception.CanNotConvertException;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.Customer;
import com.example.RookieShop.repository.UserRepository;
import com.example.RookieShop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestControllerAdvice
@CrossOrigin
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerService service;
    @Autowired
    UserRepository userRepository;


    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer,
                                            @RequestParam(name = "userID") int userID) {
        try {
            return ResponseEntity.ok(new CustomerDTO().toDTO(service.save(customer, userID)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert to CustomerDTO", e);
        }
    }
    @PostMapping("/customers/avatar")
    public ResponseEntity<?> avatarCustomer(@RequestBody String avatar,
                                            @RequestParam(name = "userID") int userID) {
        try {
            return ResponseEntity.ok(service.updateAvatar(avatar,userID));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert to CustomerDTO", e);
        }
    }
    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable int id) {
        Customer customer = service.getByID(id);
        if(customer!=null)
        {
            try {

                return ResponseEntity.ok(new CustomerDTO().toDTO(customer));
            } catch (Exception e) {
                throw new CanNotConvertException("Can't convert to CustomerDTO", e);
            }
        }
        else {
            throw new NotFoundEntityException("Not found Customer with user id is : " + id);
        }


    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomer() {
        try {
            return ResponseEntity.ok(new CustomerDTO().toListDTO(service.getAll()));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert to CustomerDTO", e);
        }
    }

    @DeleteMapping("/customers")
    public ResponseEntity<?> deleteCustomer(@RequestParam(name = "userID") int userID) {
        return ResponseEntity.ok(service.deleteByUserId(userID));
    }

    @PatchMapping("/customers")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO customerDTO,
                                            @RequestParam(name = "userID") int userID) {

        return ResponseEntity.ok(new CustomerDTO().toDTO(service.udpateById(customerDTO, userID)));
    }

}
