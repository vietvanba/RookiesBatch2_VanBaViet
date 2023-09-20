package com.example.RookieShop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "product_id")
    private int product_id;
    @JoinColumn(name = "product_name")
    @NotBlank
    private String product_name;
    @ManyToOne
    @JoinColumn(name = "product_categories_id")
    private Category product_categories;
    @JoinColumn(name = "price")
    @NotNull
    @Min(0)
    private double price;
    @JoinColumn(name = "description")
    @NotNull
    private String description;
    @JoinColumn(name = "number")
    @NotNull
    @Min(0)
    private int number;
    @JoinColumn(name = "rate")
    @Max(5)
    private float rate;
    @JoinColumn(name = "number_rate")
    @Min(0)
    private int number_rate;
    @JoinColumn(name = "created_date")
    private Timestamp created_date;
    @JoinColumn(name = "updated_date")
    private Timestamp updated_date;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductComment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImage> images = new ArrayList<>();

    public Product(int product_id, String product_name, Category product_categories,
                   double price, String description, int number, float rate,
                   int number_rate, Timestamp created_date, Timestamp updated_date/*,
                   List<ProductImage> images*/) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_categories = product_categories;
        this.price = price;
        this.description = description;
        this.number = number;
        this.rate = rate;
        this.number_rate = number_rate;
        this.created_date = created_date;
        this.updated_date = updated_date;
//        this.images = images;
    }

    public Product(int product_id, String product_name,
                   double price, String description, int number, float rate,
                   int number_rate, Timestamp created_date, Timestamp updated_date/*,
                   List<ProductImage> images*/) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.number = number;
        this.rate = rate;
        this.number_rate = number_rate;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }
}
