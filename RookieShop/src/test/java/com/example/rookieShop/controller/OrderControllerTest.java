package com.example.RookieShop.controller;


import com.example.RookieShop.dto.OrderDTO;
import com.example.RookieShop.model.Order;

import com.example.RookieShop.model.OrderStatus;
import com.example.RookieShop.model.Role;
import com.example.RookieShop.model.User;
import com.example.RookieShop.repository.OrderRepository;
import com.example.RookieShop.service.OrderService;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService service;
    @MockBean
    private OrderRepository repository;

    private List<Order> list;
    private Order order1;
    private Order order2;
    private User user;
    private OrderDTO orderDTO;
    private OrderDTO orderDTO2;

    @BeforeEach
    public void setUp() {
        this.list = new ArrayList<>();
        this.user = new User(1,"username", "password", "first_name", "last_name", "email"
                ,new Role(1,"ROLE_USER"));
        this.order1 = new Order(1, user, "first_name", "last_name",
                "address", "0989898767", 9200, new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), OrderStatus.FINISHED);
        this.order2 = new Order(2, user, "first_name", "last_name",
                "address", "0989898767", 9200, new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), OrderStatus.CANCEL);
        this.list.add(order1);
        this.list.add(order2);
        this.orderDTO = new OrderDTO(OrderStatus.DELIVERY_ORDER);

    }

    public static String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void saveOrder() throws Exception {
        Mockito.when(service.save(any(Order.class), anyString())).thenReturn(order1);
        this.mockMvc.perform(post("/api/orders").param("userName","username").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(order1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.first_name", Matchers.equalTo("first_name")))
                .andExpect(jsonPath("$.last_name", Matchers.equalTo("last_name")))
                .andExpect(jsonPath("$.address", Matchers.equalTo("address")))
                .andExpect(jsonPath("$.phone", Matchers.equalTo("0989898767")))
                .andExpect(jsonPath("$.total_price", Matchers.equalTo(9200.0)))
                .andExpect(jsonPath("$.status",Matchers.equalTo("FINISHED")));
    }

    @Test
    public void getOrder_IfFound() throws Exception {
        Mockito.when(service.getByID(anyInt())).thenReturn(order1);
        this.mockMvc.perform(get("/api/orders/{id}",1)).andExpect(status().isOk())
                .andExpect(jsonPath("$.order_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.first_name", Matchers.equalTo("first_name")))
                .andExpect(jsonPath("$.last_name", Matchers.equalTo("last_name")))
                .andExpect(jsonPath("$.address", Matchers.equalTo("address")))
                .andExpect(jsonPath("$.phone", Matchers.equalTo("0989898767")))
                .andExpect(jsonPath("$.total_price", Matchers.equalTo(9200.0)))
                .andExpect(jsonPath("$.status",Matchers.equalTo("FINISHED")));
    }

    @Test
    public void getAllOrder_IfFound() throws Exception {
        Mockito.when(service.getAll()).thenReturn(list);
        this.mockMvc.perform(get("/api/orders")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].order_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].first_name", Matchers.equalTo("first_name")))
                .andExpect(jsonPath("$[0].last_name", Matchers.equalTo("last_name")))
                .andExpect(jsonPath("$[0].address", Matchers.equalTo("address")))
                .andExpect(jsonPath("$[0].phone", Matchers.equalTo("0989898767")))
                .andExpect(jsonPath("$[0].total_price", Matchers.equalTo(9200.0)))
                .andExpect(jsonPath("$[0].status",Matchers.equalTo("FINISHED")));
    }
    @Test
    public void getOrderByUserId_IfFound() throws Exception {
        Mockito.when(service.getAllOrderByUser(anyInt())).thenReturn(list);
        this.mockMvc.perform(get("/api/orders/user_id").param("id","1")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].order_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].first_name", Matchers.equalTo("first_name")))
                .andExpect(jsonPath("$[0].last_name", Matchers.equalTo("last_name")))
                .andExpect(jsonPath("$[0].address", Matchers.equalTo("address")))
                .andExpect(jsonPath("$[0].phone", Matchers.equalTo("0989898767")))
                .andExpect(jsonPath("$[0].total_price", Matchers.equalTo(9200.0)))
                .andExpect(jsonPath("$[0].status",Matchers.equalTo("FINISHED")));
    }

    @Test
    public void updatestatusOrder_Success() throws Exception {
        Mockito.when(service.updateById(any(),anyInt())).thenReturn(order1);

        this.mockMvc.perform(patch("/api/orders/{id}",1).param("status", "FINISHED").contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status()
                .isOk())
                .andExpect(jsonPath("$.order_id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.first_name", Matchers.equalTo("first_name")))
                .andExpect(jsonPath("$.last_name", Matchers.equalTo("last_name")))
                .andExpect(jsonPath("$.address", Matchers.equalTo("address")))
                .andExpect(jsonPath("$.phone", Matchers.equalTo("0989898767")))
                .andExpect(jsonPath("$.total_price", Matchers.equalTo(9200.0)))
                .andExpect(jsonPath("$.status",Matchers.equalTo("FINISHED")));

    }
    @Test
    public void updateCancelOrder_Success() throws Exception {
        Mockito.when(service.updateById(any(),anyInt())).thenReturn(order2);

        this.mockMvc.perform(patch("/api/orders/cancel").param("id", "1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
                .andExpect(jsonPath("$.order_id", Matchers.equalTo(2)))
                .andExpect(jsonPath("$.first_name", Matchers.equalTo("first_name")))
                .andExpect(jsonPath("$.last_name", Matchers.equalTo("last_name")))
                .andExpect(jsonPath("$.address", Matchers.equalTo("address")))
                .andExpect(jsonPath("$.phone", Matchers.equalTo("0989898767")))
                .andExpect(jsonPath("$.total_price", Matchers.equalTo(9200.0)))
                .andExpect(jsonPath("$.status",Matchers.equalTo("CANCEL")));

    }
//
//    @Test
//    public void testDeleteOrder_Success() throws Exception {
//
//
//        Mockito.when(service.deleteById(anyInt())).thenReturn("Delete successful");
//
//
//        MvcResult requestResult = mockMvc.perform(delete("/api/categories").param("id",
//                "1")
//                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//                .andReturn();
//        assertEquals(requestResult.getResponse().getContentAsString(),"Delete successful");
//
//    }
}
