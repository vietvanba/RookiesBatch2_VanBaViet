package com.example.RookieShop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "customer_id")
    private int customer_id;
    @JoinColumn(name = "address")
    @NotBlank(message = "Không được để trống address")
    @Size(max =256,message = "Độ dài tối đa là 256 kí tự")
    private String address;
    @JoinColumn(name = "phone")
    @NotBlank(message = "Không được để trống phone")
    @Size(max = 10,min = 10,message = "Độ dài của số điện thoại phải là 10 kí tự")
    private String phone;
    @JoinColumn(name = "avatar")
    @NotBlank(message = "Không được để trống avatar")
    private String avatar;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public Customer(int customer_id, String address, String phone, String avatar,
                    User user) {
        this.customer_id = customer_id;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.user = user;
    }

    public Customer(String address, String phone, String avatar, User user) {
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.user = user;
    }
}
