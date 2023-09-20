package com.example.RookieShop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product_categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "product_categories_id")
    private int product_categories_id;
    @JoinColumn(name = "name")
    @NotBlank(message = "Không được để trống name")
    @Size(max = 50,message = "Độ dài tối đa là 50 kí tự")
    private String name;
    @JoinColumn(name = "description")
    @NotBlank(message = "Không được để trống description")
    @Size(max = 256,message = "Độ dài tối đa là 256 kí tự")
    private String description;
    @OneToMany(mappedBy = "product_categories", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public Category(int product_categories_id, String name, String description) {
        super();
        this.product_categories_id = product_categories_id;
        this.name = name;
        this.description = description;
    }

}
