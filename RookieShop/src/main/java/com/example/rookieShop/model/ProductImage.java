package com.example.RookieShop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "product_image_id")
    private int imageId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @JoinColumn(name = "url")
    @NotBlank
    private String url;
    @JoinColumn(name = "description")
    @NotBlank
    @Size(max = 256)
    private String description;

    public ProductImage(int imageId, Product product, String url, String description) {
        this.imageId = imageId;
        this.product = product;
        this.url = url;
        this.description = description;
    }
}