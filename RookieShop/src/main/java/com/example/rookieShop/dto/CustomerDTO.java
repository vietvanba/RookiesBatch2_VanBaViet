package com.example.RookieShop.dto;

import com.example.RookieShop.model.Customer;
import com.example.RookieShop.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private UserDTO userDTO;
    private String address;
    private String phone;
    private String avatar;

    public CustomerDTO(UserDTO userDTO, String address, String phone,
                       String avatar) {
        this.userDTO = userDTO;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
    }
    public CustomerDTO toDTO(Customer customer) {
        //UserDTO userDTO = new UserDTO().toDTO(customer.getUser());
        CustomerDTO customerDTO = new CustomerDTO(null,
                customer.getAddress(),
                customer.getPhone(),
                customer.getAvatar());
        return customerDTO;
    }

    public Customer toEntity(CustomerDTO customerDTO) {
        User user = new UserDTO().toEntity(customerDTO.getUserDTO());
        Customer customer = new Customer(user.getUser_id(), customerDTO.getAddress(),
                customerDTO.getPhone(),
                customerDTO.getAvatar(),
                user);
        return customer;
    }

    public List<CustomerDTO> toListDTO(List<Customer> listCustomer) {
        List<CustomerDTO> listCustomerDTO = new ArrayList<>();
        listCustomer.forEach(x -> {
            listCustomerDTO.add(new CustomerDTO(new UserDTO().toDTO(x.getUser()),
                    x.getAddress(),
                    x.getPhone(),
                    x.getAvatar()));
        });
        return listCustomerDTO;

    }
}

