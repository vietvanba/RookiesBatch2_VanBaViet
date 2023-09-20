package com.example.RookieShop.controller;


import com.example.RookieShop.dto.ProductDTO;
import com.example.RookieShop.dto.ProductImageDTO;
import com.example.RookieShop.model.Category;
import com.example.RookieShop.model.Order;
import com.example.RookieShop.model.OrderDetail;
import com.example.RookieShop.model.Product;
import com.example.RookieShop.repository.ProductRepository;
import com.example.RookieShop.service.ProductService;
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

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService service;
    @MockBean
    private ProductRepository repository;

    private List<Product> list;
    private List<Product> list2;
    private Category category;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private ProductDTO productDTO;
    private OrderDetail orderDetail;

    @BeforeEach
    public void setUp() {
        this.list = new ArrayList<>();
        this.list2 = new ArrayList<>();


        this.category = new Category(1, "cate", "des");
        this.product1 = new Product(1, "prod", this.category,
                0.0, "description", 200, Float.parseFloat("4.0"),
                5, new Timestamp(System.currentTimeMillis()), null);
        this.product2 = new Product(2, "product_name", this.category,
                0.0, "description", 200, Float.parseFloat("4.0"),
                5, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        this.product3 = new Product(3, "product_name_3", this.category,
                0.0, "description", 200, Float.parseFloat("4.0"),
                5, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        this.product1 = new Product(1, "prod", this.category,
                0.0, "description", 200,
                Float.parseFloat(String.valueOf((4.0 * 5 + 5) / 6)),
                6, new Timestamp(System.currentTimeMillis()), null);
        this.orderDetail = new OrderDetail(1, null, product1, 1, 111.111, 5);
        this.list2.add(product2);
        this.list2.add(product3);
        this.list.add(product1);
        this.list.add(product2);
        this.productDTO = new ProductDTO("product_name", "description");

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
    public void saveProduct() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss" +
                ".SSSZ");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Mockito.when(service.save(any(Product.class), anyInt())).thenReturn(product1);
        this.mockMvc.perform(post("/api/products").param("categoryID", "1").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(product1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.product_name", Matchers.equalTo("product_name")))
                .andExpect(jsonPath("$.price", Matchers.equalTo(0.0)))
                .andExpect(jsonPath("$.description", Matchers.equalTo("description")))
                .andExpect(jsonPath("$.number", Matchers.equalTo(200)))
                .andExpect(jsonPath("$.rate", Matchers.equalTo(4.0)))
                .andExpect(jsonPath("$.number_rate", Matchers.equalTo(5)))
                .andExpect(jsonPath("$.updated_date", Matchers.equalTo(null)))
                .andExpect(jsonPath("$.imageDTOS",
                        Matchers.equalTo(new ProductImageDTO().toListDTO(product1.getImages()))))
                .andExpect(jsonPath("$.product_categories.product_categories_id",
                        Matchers.equalTo(1)));
    }

    @Test
    public void getProduct_IfFound() throws Exception {
        Mockito.when(service.getByID(anyInt())).thenReturn(product1);
        this.mockMvc.perform(get("/api/products/{id}", 1)).andExpect(status().isOk())
                .andExpect(jsonPath("$.product_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.product_name", Matchers.equalTo("product_name")))
                .andExpect(jsonPath("$.price", Matchers.equalTo(0.0)))
                .andExpect(jsonPath("$.description", Matchers.equalTo("description")))
                .andExpect(jsonPath("$.number", Matchers.equalTo(200)))
                .andExpect(jsonPath("$.rate", Matchers.equalTo(4.0)))
                .andExpect(jsonPath("$.number_rate", Matchers.equalTo(5)))
                .andExpect(jsonPath("$.updated_date", Matchers.equalTo(null)))
                .andExpect(jsonPath("$.imageDTOS",
                        Matchers.equalTo(new ProductImageDTO().toListDTO(product1.getImages()))))
                .andExpect(jsonPath("$.product_categories.product_categories_id",
                        Matchers.equalTo(1)));
    }

    @Test
    public void getAllProductByCategories_IfFound() throws Exception {
        Mockito.when(service.getByCategoriesID(any())).thenReturn(list);
        this.mockMvc.perform(get("/api/products/categories").param("categoriesID", "1")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].product_name", Matchers.equalTo("product_name")))
                .andExpect(jsonPath("$[0].price", Matchers.equalTo(0.0)))
                .andExpect(jsonPath("$[0].description", Matchers.equalTo("description")))
                .andExpect(jsonPath("$[0].number", Matchers.equalTo(200)))
                .andExpect(jsonPath("$[0].rate", Matchers.equalTo(4.0)))
                .andExpect(jsonPath("$[0].number_rate", Matchers.equalTo(5)))
                .andExpect(jsonPath("$[0].updated_date", Matchers.equalTo(null)))
                .andExpect(jsonPath("$[0].imageDTOS",
                        Matchers.equalTo(new ProductImageDTO().toListDTO(product1.getImages()))))
                .andExpect(jsonPath("$[0].product_categories.product_categories_id",
                        Matchers.equalTo(1)));
    }

    @Test
    public void getAllProductSearch_IfFound() throws Exception {
        Mockito.when(service.search("product_name")).thenReturn(list2);
        this.mockMvc.perform(get("/api/products/search/{key}", "product_name")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product_id", Matchers.equalTo(2)))
                .andExpect(jsonPath("$[0].product_name", Matchers.equalTo("product_name")))
                .andExpect(jsonPath("$[0].price", Matchers.equalTo(0.0)))
                .andExpect(jsonPath("$[0].description", Matchers.equalTo("description")))
                .andExpect(jsonPath("$[0].number", Matchers.equalTo(200)))
                .andExpect(jsonPath("$[0].rate", Matchers.equalTo(4.0)))
                .andExpect(jsonPath("$[0].number_rate", Matchers.equalTo(5)))
                .andExpect(jsonPath("$[0].imageDTOS",
                        Matchers.equalTo(new ProductImageDTO().toListDTO(product2.getImages()))))
                .andExpect(jsonPath("$[0].product_categories.product_categories_id",
                        Matchers.equalTo(1)));
    }

    @Test
    public void rateProduct_Success() throws Exception {
        Mockito.when(service.rate(anyInt(), anyInt(), any())).thenReturn(product4);
        this.mockMvc.perform(patch("/product/rate/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                .content(toJson(productDTO))).andExpect(status()
                .isOk())
                .andExpect(jsonPath("$.product_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.product_name", Matchers.equalTo("product_name")))
                .andExpect(jsonPath("$.price", Matchers.equalTo(0.0)))
                .andExpect(jsonPath("$.description", Matchers.equalTo("description")))
                .andExpect(jsonPath("$.number", Matchers.equalTo(200)))
                .andExpect(jsonPath("$.rate", Matchers.equalTo(product4.getRate())))
                .andExpect(jsonPath("$.number_rate", Matchers.equalTo(6)))
                .andExpect(jsonPath("$.imageDTOS",
                        Matchers.equalTo(new ProductImageDTO().toListDTO(product1.getImages()))))
                .andExpect(jsonPath("$[0].product_categories.product_categories_id",
                        Matchers.equalTo(1)));

    }

    @Test
    public void testDeleteProduct_Success() throws Exception {
        Mockito.when(service.deleteById(anyInt())).thenReturn("Delete successful");
        MvcResult requestResult = mockMvc.perform(delete("/api/products").param("id",
                "1")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
        assertEquals(requestResult.getResponse().getContentAsString(), "Delete successful");
    }
}
