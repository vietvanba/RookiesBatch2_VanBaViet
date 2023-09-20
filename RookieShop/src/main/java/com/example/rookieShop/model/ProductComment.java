package com.example.RookieShop.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product_comments")
@Getter
@Setter
@NoArgsConstructor
public class ProductComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "product_comment_id")
    private int commentId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @JoinColumn(name = "time")
    private Timestamp time;
    @JoinColumn(name = "content")
    @NotBlank
    @Size(max = 256)
    private String content;

    public ProductComment(int commentId, User user, Product product, Timestamp time, String content) {
        this.commentId = commentId;
        this.user = user;
        this.product = product;
        this.time = time;
        this.content = content;
    }
}