package com.example.RookieShop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "order_detail_id")
    private int orderDetailId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @JoinColumn(name = "number")
    @NotNull
    @Min(1)
    private int number;
    @JoinColumn(name = "price")
    @NotNull
    private double price;
    @JoinColumn(name = "rate")
    @Max(5)
    private float rate;

    public OrderDetail(int orderDetailId, Order order, Product product, int number, double price, float rate) {
        this.orderDetailId = orderDetailId;
        this.order = order;
        this.product = product;
        this.number = number;
        this.price = price;
        this.rate = rate;
    }
}