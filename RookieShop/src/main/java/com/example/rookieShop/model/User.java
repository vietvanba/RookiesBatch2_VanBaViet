package com.example.RookieShop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "user_id")
    private int user_id;
    @JoinColumn(name = "user_name", unique = true)
    @NotBlank
    @Size(min = 5, max = 50)
    private String username;

    @JoinColumn(name = "password")
    @NotBlank
    @Size(min = 5, max = 256)
    private String password;
    @JoinColumn(name = "first_name")
    @NotBlank
    @Size(max = 50)
    private String first_name;
    @JoinColumn(name = "email")
    @NotBlank
    @Size(max = 50)
    private String email;
    @JoinColumn(name = "last_name")
    @NotBlank
    @Size(max = 50)
    private String last_name;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ProductComment> comments = new ArrayList<>();
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Customer customer;

    public User(String username, String password, String first_name,
                String last_name, String email) {
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.email = email;
        this.last_name = last_name;
    }

    public User(int user_id, String user_name, String first_name, String last_name, String email) {
        this.user_id = user_id;
        this.username = user_name;
        this.first_name = first_name;
        this.email = email;
        this.last_name = last_name;
    }

    public User(String user_name, String password, String first_name,
                String last_name, String email, Role role) {
        this.user_id = user_id;
        this.username = user_name;
        this.password = password;
        this.first_name = first_name;
        this.email = email;
        this.last_name = last_name;
        this.role = role;
    }

    public User(int user_id, String username, String password, String first_name, String email, String last_name, Role role, Customer customer) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.email = email;
        this.last_name = last_name;
        this.role = role;
        this.customer = customer;
    }
    public User(int user_id, String username, String password, String first_name, String email, String last_name, Role role) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.email = email;
        this.last_name = last_name;
        this.role = role;

    }
}
