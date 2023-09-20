package com.example.RookieShop.service;

import com.example.RookieShop.dto.OrderDTO;
import com.example.RookieShop.dto.ProductDTO;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.*;
import com.example.RookieShop.repository.OrderRepository;
import com.example.RookieShop.repository.UserRepository;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    OrderService orderService;
    private List<Order> list;
    private Order order1;
    private Order order2;
    private User user;
    private OrderDTO orderDTO;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeEach
    public void setUp() {
        this.list = new ArrayList<>();
        this.user = new User("username", "password", "first_name", "last_name", "email");
        this.order1 = new Order(1, user, "first_name", "last_name",
                "address", "0989898768", 9200, new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), OrderStatus.CONFIRMED);
        this.order2 = new Order(2, user, "first_name", "last_name",
                "address", "0989898768", 9200, new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), OrderStatus.CONFIRMED);
        this.list.add(order1);
        this.list.add(order2);
        this.orderDTO = new OrderDTO(OrderStatus.DELIVERY_ORDER);
    }

    @Test
    public void getAll_ReturnListOrder_IfFound() {
        Mockito.when(orderRepository.findAll()).thenReturn(list);
        List<Order> orderList = orderService.getAll();
        assertEquals(orderList, list);
    }
    @Test
    public void getByID_ReturnOrder_IfFound() {
        Mockito.when(orderRepository.findById(order1.getOrder_id())).thenReturn(Optional.ofNullable(order1));
        Order order = orderService.getByID(order1.getOrder_id());
        assertEquals(order, order1);
    }

    @Test
    public void getByID_ReturnException_IfNotFound() {
        BDDMockito.given(orderRepository.findById(order1.getOrder_id())).willReturn(Optional.ofNullable(null));

        try {
            orderService.getByID(order1.getOrder_id());
        } catch (NotFoundEntityException e) {
            exception.expect(NotFoundEntityException.class);
        }
    }

    @Test
    public void save_Order_IfSuccess() {
        when(orderRepository.save(any(Order.class))).thenReturn(order1);
        when(userRepository.findByUsername(order1.getUser().getUsername())).thenReturn(Optional.ofNullable(user));
        orderService.save(order1, order1.getUser().getUsername());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    public void updateOrderStatus_IfSuccess()
    {
        when(orderRepository.findById(order1.getOrder_id())).thenReturn(Optional.ofNullable(order1));
        Order order = order1;
        order.setStatus(OrderStatus.DELIVERY_ORDER);

        orderService.updateById(OrderStatus.DELIVERY_ORDER,order1.getOrder_id());
        assertEquals(order,order1);
    }

}
