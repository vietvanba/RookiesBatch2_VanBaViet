package com.example.RookieShop.dto;

import com.example.RookieShop.model.Order;
import com.example.RookieShop.model.OrderStatus;
import com.example.RookieShop.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private int order_id;
    private UserDTO userDTO;
    private String first_name;
    private String last_name;
    private String address;
    private String phone;
    private double total_price;
    private Timestamp order_time;
    private Timestamp finish_time;
    private OrderStatus status;
    private List<OrderDetailDTO> orderDetailDTO;

    public OrderDTO(int order_id, UserDTO userDTO, String first_name, String last_name,
                    String address, String phone, double total_price,
                    Timestamp order_time, Timestamp finish_time, OrderStatus status,
                    List<OrderDetailDTO> orderDetailDTO) {
        this.order_id = order_id;
        this.userDTO = userDTO;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone = phone;
        this.total_price = total_price;
        this.order_time = order_time;
        this.finish_time = finish_time;
        this.status = status;
        this.orderDetailDTO = orderDetailDTO;
    }

    public OrderDTO(OrderStatus status) {
        this.status = status;
    }

    public OrderDTO toDTO(Order order) {
        UserDTO userDTO = new UserDTO().toDTO(order.getUser());
        List<OrderDetailDTO> listDetailDTO= new ArrayList<>();
        order.getOrder_details().forEach(x->{
            listDetailDTO.add(new OrderDetailDTO().toDTO(x));
        });
        OrderDTO orderDTO = new OrderDTO(order.getOrder_id(),
                userDTO,
                order.getFirst_name(),
                order.getLast_name(),
                order.getAddress(),
                order.getPhone(),
                order.getTotal_price(),
                order.getOrder_time(),
                order.getFinish_time(),
                order.getStatus(),listDetailDTO
        );


        return orderDTO;
    }

    public Order toEntity(OrderDTO orderDTO) {
        User user = new UserDTO().toEntity(orderDTO.getUserDTO());
        Order order = new Order(orderDTO.getOrder_id(),
                user,
                orderDTO.getFirst_name(),
                orderDTO.getLast_name(),
                orderDTO.getAddress(),
                orderDTO.getPhone(),
                orderDTO.getTotal_price(),
                orderDTO.getOrder_time(),
                orderDTO.getFinish_time(),
                orderDTO.getStatus()
        );
        return order;
    }

    public List<OrderDTO> toListDTO(List<Order> listOrder) {
        List<OrderDTO> listOrderDTO = new ArrayList<>();
        listOrder.forEach(x -> {
            List<OrderDetailDTO> listDetailDTO= new ArrayList<>();
            x.getOrder_details().forEach(y->{
                listDetailDTO.add(new OrderDetailDTO().toDTO(y));
            });
            listOrderDTO.add(new OrderDTO(x.getOrder_id(),
                    new UserDTO().toDTO(x.getUser()),
                    x.getFirst_name(),
                    x.getLast_name(),
                    x.getAddress(),
                    x.getPhone(),
                    x.getTotal_price(),
                    x.getOrder_time(),
                    x.getFinish_time(),
                    x.getStatus(),listDetailDTO));
        });
        return listOrderDTO;
    }
}
