package com.example.RookieShop.controller;


import com.example.RookieShop.dto.CategoryDTO;
import com.example.RookieShop.model.Category;
import com.example.RookieShop.repository.CategoryRepository;
import com.example.RookieShop.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CategoriesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService service;
    @MockBean
    private CategoryRepository repository;

    private List<Category> list;
    private Category category1;
    private Category category2;
    private Category category3;
    private CategoryDTO categoryDTO;
    private CategoryDTO categoryDTO2;

    @BeforeEach
    public void setUp() {
        this.list = new ArrayList<>();
        this.category1 = new Category(1, "Category1", "Description1");
        this.category2 = new Category(2, "Category2", "Description2");
        this.category3 = new Category(1, "Category2", "Description2");
        this.list.add(category1);
        this.list.add(category2);
        this.categoryDTO = new CategoryDTO("Category2", "Category2");

    }

    public static String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void saveCategory() throws Exception {
        Mockito.when(service.save(any(Category.class))).thenReturn(category1);
        this.mockMvc.perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(category1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_categories_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Category1")))
                .andExpect(jsonPath("$.description", Matchers.equalTo("Description1")));
    }

    @Test
    public void getCategory_IfFound() throws Exception {
        Mockito.when(service.getByID(category1.getProduct_categories_id())).thenReturn(category1);
        this.mockMvc.perform(get("/api/categories/" + category1.getProduct_categories_id())).andExpect(status().isOk())
                .andExpect(jsonPath("$.product_categories_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Category1")))
                .andExpect(jsonPath("$.description", Matchers.equalTo("Description1")));
    }

    @Test
    public void getAllCategory_IfFound() throws Exception {
        Mockito.when(service.getAll()).thenReturn(list);
        this.mockMvc.perform(get("/api/categories")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product_categories_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("Category1")))
                .andExpect(jsonPath("$[0].description",
                        Matchers.equalTo("Description1")));
    }

    @Test
    public void updateCategory_Success() throws Exception {
        Mockito.when(service.udpateById(any(CategoryDTO.class),
                anyInt())).thenReturn(category3);
        this.mockMvc.perform(patch("/api/categories").param("id", "1").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(categoryDTO))).andExpect(status()
                .isOk())
                .andExpect(jsonPath("$.product_categories_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Category2")))
                .andExpect(jsonPath("$.description", Matchers.equalTo("Description2")));

    }

    @Test
    public void testDeleteCategory_Success() throws Exception {


        Mockito.when(service.deleteById(anyInt())).thenReturn("Delete successful");


        MvcResult requestResult = mockMvc.perform(delete("/api/categories").param("id",
                "1")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
        assertEquals(requestResult.getResponse().getContentAsString(),"Delete successful");

    }
}
