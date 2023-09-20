package com.example.RookieShop.dto;

import com.example.RookieShop.model.Category;
import com.example.RookieShop.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private int product_id;
    private String product_name;
    private CategoryDTO product_categories;
    private double price;
    private String description;
    private int number;
    private float rate;
    private int number_rate;
    private Timestamp created_date;
    private Timestamp updated_date;
    private List<ProductImageDTO> ImageDTOS;

    public ProductDTO(int product_id, String product_name,
                      CategoryDTO product_categories, double price, String description,
                      int number, float rate, int number_rate, Timestamp created_date, Timestamp updated_date, List<ProductImageDTO> ImageDTOS) {
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
        this.ImageDTOS = ImageDTOS;
    }

    public ProductDTO(String product_name, String description) {
        this.product_name = product_name;
        this.description = description;
    }

    public ProductDTO(int product_id, String product_name, double price, String description, int number, float rate, int number_rate, Timestamp created_date, Timestamp updated_date, List<ProductImageDTO> imageDTOS) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.number = number;
        this.rate = rate;
        this.number_rate = number_rate;
        this.created_date = created_date;
        this.updated_date = updated_date;
        ImageDTOS = imageDTOS;
    }
    public ProductDTO toDTO(Product product) {

        return new ProductDTO(product.getProduct_id(),
                product.getProduct_name(),
                new CategoryDTO().toDTO(product.getProduct_categories()),
                product.getPrice(),
                product.getDescription(),
                product.getNumber(),
                product.getRate(),
                product.getNumber_rate(),
                product.getCreated_date(),
                product.getUpdated_date(),
                new ProductImageDTO().toListDTO(product.getImages()));
    }

    public Product toEntity(ProductDTO productDTO) {

        return new Product(productDTO.getProduct_id(),
                productDTO.getProduct_name(),
                productDTO.getPrice(),
                productDTO.getDescription(),
                productDTO.getNumber(),
                productDTO.getRate(),
                productDTO.getNumber_rate(),
                productDTO.getCreated_date(),
                productDTO.getUpdated_date()
//                , imageConvert.toListEntity(productDTO.getImageDTOS())
        );
    }

    public List<ProductDTO> toListDTO(List<Product> listProductCategories) {

        List<ProductDTO> listProductDTO = new ArrayList<>();
        listProductCategories.forEach(x -> {
            listProductDTO.add(new ProductDTO(x.getProduct_id(),
                    x.getProduct_name(),
                    new CategoryDTO().toDTO(x.getProduct_categories()),
                    x.getPrice(),
                    x.getDescription(),
                    x.getNumber(),
                    x.getRate(),
                    x.getNumber_rate(),
                    x.getCreated_date(),
                    x.getUpdated_date(),
                    new ProductImageDTO().toListDTO(x.getImages())));
        });

        return listProductDTO;
    }

}
