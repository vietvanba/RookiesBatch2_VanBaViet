package com.example.RookieShop.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "order_id")
    private int order_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @JoinColumn(name = "first_name")
    @NotBlank(message = "Không được để trống First Name")
    @Size(max = 50, message = "Độ dài tối đa là 50 kí tự")
    private String first_name;
    @JoinColumn(name = "last_name")
    @NotBlank(message = "Không được để trống Last Name")
    @Size(max = 50,message = "Độ dài tối đa là 50 kí tự")
    private String last_name;
    @JoinColumn(name = "address")
    @NotBlank(message = "Không được để trống address")
    @Size(max = 256,message = "Độ dài tối đa là 256 kí tự")
    private String address;
    @JoinColumn(name = "phone")
    @NotBlank(message = "Không được để trống phone")
    @Size(max = 10, min = 10,message = "Số điện thoại phải bằng 10 kí tự")
    private String phone;
    @JoinColumn(name = "total_price")
    @Min(value = 0,message = "Số tiền phải lớn hơn hoặc bằng 0")
    private double total_price;
    @JoinColumn(name = "order_time")
    private Timestamp order_time;
    @JoinColumn(name = "finish_time")
    private Timestamp finish_time;
    @JoinColumn(name = "status")
    private OrderStatus status;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    List<OrderDetail> order_details = new ArrayList<>();


    public Order(int order_id, User user, String first_name, String last_name, String address, String phone, double total_price, Timestamp order_time, Timestamp finish_time, OrderStatus status) {
        this.order_id = order_id;
        this.user = user;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone = phone;
        this.total_price = total_price;
        this.order_time = order_time;
        this.finish_time = finish_time;
        this.status = status;
    }
}