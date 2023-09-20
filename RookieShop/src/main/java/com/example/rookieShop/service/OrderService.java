package com.example.RookieShop.service;


import com.example.RookieShop.dto.OrderDTO;
import com.example.RookieShop.dto.OrderDetailDTO;
import com.example.RookieShop.exception.CanNotSaveEntityException;
import com.example.RookieShop.exception.CanNotUpdateEntityException;
import com.example.RookieShop.exception.NotEnoughNumberException;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.*;
import com.example.RookieShop.repository.OrderDetailRepository;
import com.example.RookieShop.repository.OrderRepository;
import com.example.RookieShop.repository.ProductRepository;
import com.example.RookieShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
//@Component
public class OrderService {
    @Autowired
    OrderRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderDetailRepository detailRepository;
    @Autowired
    ProductRepository productRepository;
    public Order save(Order order, String userName) {
        User user = userRepository.findByUsername(userName).orElse(null);
        final double[] total = {0};
        if (user == null) {
            throw new NotFoundEntityException("Not found user with username: " + userName);
        }
        try {
            order.getOrder_details().forEach(x->{
                total[0] = total[0] +(x.getNumber()*x.getProduct().getPrice());
                order.setTotal_price(total[0]);
            });
            order.setUser(user);
            order.setOrder_time(new Timestamp(System.currentTimeMillis()));
            order.setStatus(OrderStatus.CONFIRMED);
            repository.save(order);
            order.getOrder_details().forEach(x -> {
                x.setOrder(order);
                Product product = productRepository.getById(x.getProduct().getProduct_id());
                if (product.getNumber() < x.getNumber()) {
                    throw new NotEnoughNumberException("Product not enough");
                }
                product.setNumber(product.getNumber() - x.getNumber());
                x.setProduct(product);
                productRepository.save(product);
                detailRepository.save(x);
            });
            return order;
        } catch (Exception e) {
            throw new CanNotSaveEntityException("Can't save Order to database.Please " +
                    "check again", e);
        }

    }

    public Order getByID(int id) {
        Order order = repository.findById(id).orElse(null);
        if (order != null) {
            return order;
        } else {
            throw new NotFoundEntityException("Not found the order with id" + id);
        }
    }

    public List<Order> getAll() {
        List<Order> list = repository.findAll();
        if (list.size() != 0) {
            return list;
        } else {
            throw new NotFoundEntityException("Can't get any Order");
        }
    }

    public List<Order> getAllOrderByUser(int id) {
        List<Order> list = repository.findAllOrderByUser(id);
        if (list.size() != 0) {
            return list;
        } else {
            throw new NotFoundEntityException("Can't get any Order");
        }
    }

    public Order updateById(OrderStatus status, int id) {
        Order order = repository.findById(id).orElse(null);
        if (order != null) {
            try {
                if (order.getStatus() == OrderStatus.FINISHED)
                    throw new CanNotUpdateEntityException("Can't update status because " +
                            "order has been finished");
                if(status==OrderStatus.FINISHED)
                    order.setFinish_time(new Timestamp(System.currentTimeMillis()));
                order.setStatus(status);
                return repository.save(order);
            } catch (Exception e) {
                throw new CanNotUpdateEntityException("Can't update Category with id " + id, e);
            }
        } else {
            throw new NotFoundEntityException("Not found the order with id" + id);
        }


    }


}
