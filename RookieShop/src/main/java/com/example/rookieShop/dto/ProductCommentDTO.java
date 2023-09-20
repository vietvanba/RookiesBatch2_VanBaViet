package com.example.RookieShop.dto;

import com.example.RookieShop.model.Product;
import com.example.RookieShop.model.ProductComment;
import com.example.RookieShop.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductCommentDTO {
    private int commentId;
    private UserDTO userDTO;
    private ProductDTO productDTO;
    private Timestamp time;
    private String content;

    public ProductCommentDTO(int commentId, UserDTO userDTO, ProductDTO productDTO, Timestamp time, String content) {
        this.commentId = commentId;
        this.userDTO = userDTO;
        this.productDTO = productDTO;
        this.time = time;
        this.content = content;
    }
    public ProductCommentDTO toDTO(ProductComment productComment) {
        UserDTO userDTO = new UserDTO().toDTO(productComment.getUser());
        ProductDTO productDTO = new ProductDTO().toDTO(productComment.getProduct());
        ProductCommentDTO productCommentDTO = new ProductCommentDTO(
                productComment.getCommentId(),
                userDTO,
                productDTO,
                productComment.getTime(),
                productComment.getContent()
        );
        return productCommentDTO;
    }

    public ProductComment toEntity(ProductCommentDTO productCommentDTO) {
        User user = new UserDTO().toEntity(productCommentDTO.getUserDTO());
        Product product = new ProductDTO().toEntity(productCommentDTO.getProductDTO());
        ProductComment productComment = new ProductComment(
                productCommentDTO.getCommentId(),
                user,
                product,
                productCommentDTO.getTime(),
                productCommentDTO.getContent()

        );
        return productComment;
    }

    public List<ProductCommentDTO> toListDTO(List<ProductComment> listProductComment) {
        List<ProductCommentDTO> listProductCommentDTO = new ArrayList<>();
        listProductComment.forEach(x -> {
            UserDTO userDTO = new UserDTO().toDTO(x.getUser());
            ProductDTO productDTO = new ProductDTO().toDTO(x.getProduct());
            listProductCommentDTO.add(new ProductCommentDTO(x.getCommentId(),
                    userDTO,
                    productDTO,
                    x.getTime(),
                    x.getContent()));
        });
        return listProductCommentDTO;
    }
}
