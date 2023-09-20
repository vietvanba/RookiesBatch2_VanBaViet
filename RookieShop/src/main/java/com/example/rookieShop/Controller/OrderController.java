package com.example.RookieShop.controller;


import com.example.RookieShop.dto.OrderDTO;
import com.example.RookieShop.dto.OrderDetailDTO;
import com.example.RookieShop.exception.CanNotConvertException;
import com.example.RookieShop.exception.NotEnoughNumberException;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.Order;
import com.example.RookieShop.model.OrderDetail;
import com.example.RookieShop.model.OrderStatus;
import com.example.RookieShop.model.Product;
import com.example.RookieShop.repository.OrderDetailRepository;
import com.example.RookieShop.repository.ProductRepository;
import com.example.RookieShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;

@RestControllerAdvice
@CrossOrigin
@RequestMapping("/api")
public class OrderController {
    @Autowired
    OrderService service;



    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@Valid @RequestBody Order order,
                                         @RequestParam(name = "userName") String userName) {
        try {
            return ResponseEntity.ok(new OrderDTO().toDTO(service.save(order, userName)));

        } catch (Exception e) {
            throw new NotFoundEntityException("Order failed", e);
        }

    }



    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrder(@PathVariable int id) {
        try {
            Order order = service.getByID(id);
            return ResponseEntity.ok(new OrderDTO().toDTO(order));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(("Can't convert Entity to DTO"));
        }

    }

    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrder() {
        try {
            return ResponseEntity.ok(new OrderDTO().toListDTO(service.getAll()));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert to Entity to DTO", e);

        }

    }
    @GetMapping("/orders/user_id")
    public ResponseEntity<?> getAllOrderByUser(@RequestParam(name = "id") int id) {
        try {
            return ResponseEntity.ok(new OrderDTO().toListDTO(service.getAllOrderByUser(id)));
        } catch (Exception e) {
            throw new CanNotConvertException("Can't convert to Entity to DTO", e);

        }

    }

    @PatchMapping("/orders/{id}")
    public ResponseEntity<?> updateStatusOrder(@PathVariable int id,
                                               @RequestParam(name = "status") OrderStatus status) {

        return ResponseEntity.ok(new OrderDTO().toDTO(service.updateById(status, id)));
    }
    @PatchMapping("/orders/cancel")
    public ResponseEntity<?> cancelStatusOrder(
                                               @RequestParam(name = "id") int id) {

        return ResponseEntity.ok(new OrderDTO().toDTO(service.updateById(OrderStatus.CANCEL,
                id)));
    }

}
