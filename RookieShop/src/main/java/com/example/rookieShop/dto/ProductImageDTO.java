package com.example.RookieShop.dto;

import com.example.RookieShop.model.Product;
import com.example.RookieShop.model.ProductImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductImageDTO {
    private int image_id;
    private ProductDTO productDTO;
    private String url;
    private String description;

    public ProductImageDTO(int image_id, ProductDTO productDTO, String url, String description) {
        this.image_id = image_id;
        this.productDTO = productDTO;
        this.url = url;
        this.description = description;
    }

    public ProductImageDTO(int image_id, String url, String description) {
        this.image_id = image_id;
        this.url = url;
        this.description = description;
    }
    public ProductImageDTO toDTO(ProductImage productImage) {
        ProductDTO productDTO = new ProductDTO().toDTO(productImage.getProduct());
        ProductImageDTO productImageDTO = new ProductImageDTO(
                productImage.getImageId(),
                productDTO,
                productImage.getUrl(),
                productImage.getDescription()
        );
        return productImageDTO;
    }

    public ProductImage toEntity(ProductImageDTO productImageDTO) {

        Product product = new ProductDTO().toEntity(productImageDTO.getProductDTO());
        ProductImage productImage = new ProductImage(
                productImageDTO.getImage_id(),
                product,
                productImageDTO.getUrl(),
                productImageDTO.getDescription()

        );
        return productImage;
    }

    public List<ProductImageDTO> toListDTO(List<ProductImage> listProductImages) {
        List<ProductImageDTO> listProductImageDTO = new ArrayList<>();
        listProductImages.forEach(x -> {
//            ProductDTO productDTO = productConvert.toDTO(x.getProduct());
            listProductImageDTO.add(new ProductImageDTO(x.getImageId(),
//                    productDTO,
                    x.getUrl(),
                    x.getDescription()));
        });
        return listProductImageDTO;
    }
    public List<ProductImage> toListEntity(List<ProductImageDTO> listProductImageDTOS) {
        List<ProductImage> listProductImage = new ArrayList<>();
        listProductImageDTOS.forEach(x -> {
            Product product = new ProductDTO().toEntity(x.getProductDTO());
            listProductImage.add(new ProductImage(x.getImage_id(),
                    product,
                    x.getUrl(),
                    x.getDescription()));
        });
        return listProductImage;
    }
}
