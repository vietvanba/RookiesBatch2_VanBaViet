package com.example.RookieShop.dto;

import com.example.RookieShop.model.Order;
import com.example.RookieShop.model.OrderDetail;
import com.example.RookieShop.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailDTO {
    private int order_detail_id;
    private ProductDTO productDTO;
    private OrderDTO orderDTO;
    private int number;
    private double price;
    private float rate;

    public OrderDetailDTO(int order_detail_id, ProductDTO productDTO, OrderDTO orderDTO, int number, double price, float rate) {
        this.order_detail_id = order_detail_id;
        this.productDTO = productDTO;
        this.orderDTO = orderDTO;
        this.number = number;
        this.price = price;
        this.rate = rate;
    }

    public OrderDetailDTO(int order_detail_id, int number, double price, float rate) {
        this.order_detail_id = order_detail_id;
        this.number = number;
        this.price = price;
        this.rate = rate;
    }

    public OrderDetailDTO(int order_detail_id, ProductDTO productDTO, int number, double price, float rate) {
        this.order_detail_id = order_detail_id;
        this.productDTO = productDTO;
        this.number = number;
        this.price = price;
        this.rate = rate;
    }
    public OrderDetailDTO toDTO(OrderDetail orderDetail) {

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO(
                orderDetail.getOrderDetailId(),
               new ProductDTO().toDTO(orderDetail.getProduct()),
//                orderDTO,
                orderDetail.getNumber(),
                orderDetail.getPrice(),
                orderDetail.getRate()
        );
        return orderDetailDTO;
    }

    public OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {

        Product product = new ProductDTO().toEntity(orderDetailDTO.getProductDTO());
        Order order = new OrderDTO().toEntity(orderDetailDTO.getOrderDTO());
        OrderDetail orderDetail = new OrderDetail(
                orderDetailDTO.getOrder_detail_id(),
                order,
                product,
                orderDetailDTO.getNumber(),
                orderDetailDTO.getPrice(),
                orderDetailDTO.getRate()
        );
        return orderDetail;
    }

    public List<OrderDetailDTO> toListDTO(List<OrderDetail> listOrderDetails) {
        List<OrderDetailDTO> listOrderDetailDTO = new ArrayList<>();

        listOrderDetails.forEach(x -> {
            ProductDTO productDTO = new ProductDTO().toDTO(x.getProduct());
            listOrderDetailDTO.add(new OrderDetailDTO(x.getOrderDetailId(),
                    productDTO,
//                    orderDTO,
                    x.getNumber(),
                    x.getPrice(),
                    x.getRate()));
        });
        return listOrderDetailDTO;
    }
    public List<OrderDetail> toListEntity(List<OrderDetailDTO> listOrderDetailDTOS) {
        List<OrderDetail> listOrderDetail = new ArrayList<>();
        listOrderDetailDTOS.forEach(x -> {
            Product product = new ProductDTO().toEntity(x.getProductDTO());
            Order order = new OrderDTO().toEntity(x.getOrderDTO());
            listOrderDetail.add(new OrderDetail(x.getOrder_detail_id(),
                    order,
                    product,
                    x.getNumber(),
                    x.getPrice(),
                    x.getRate()));
        });
        return listOrderDetail;
    }
}
