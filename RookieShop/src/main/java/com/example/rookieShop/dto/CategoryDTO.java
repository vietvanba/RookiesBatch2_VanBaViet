package com.example.RookieShop.dto;

import com.example.RookieShop.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private int product_categories_id;
    private String name;
    private String description;

    public CategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CategoryDTO(int product_categories_id, String name, String description) {
        this.product_categories_id = product_categories_id;
        this.name = name;
        this.description = description;
    }
    public CategoryDTO toDTO(Category category) {

        return new CategoryDTO(category.getProduct_categories_id(),
                category.getName(),
                category.getDescription());
    }
    public Category toEntity(CategoryDTO categoryDTO) {

        return new Category(categoryDTO.getProduct_categories_id(),
                categoryDTO.getName(), categoryDTO.getDescription());
    }

    public List<CategoryDTO> toListDTO(List<Category> listProductCategories) {

        List<CategoryDTO> listCategoryDTO = new ArrayList<>();
        listProductCategories.forEach(x -> {
            listCategoryDTO.add(new CategoryDTO(x.getProduct_categories_id(), x.getName(), x.getDescription()));
        });

        return listCategoryDTO;
    }

}
