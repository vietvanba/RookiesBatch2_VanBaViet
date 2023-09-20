package com.example.RookieShop.dto;

import com.example.RookieShop.model.Role;
import com.example.RookieShop.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private int user_id;
    private String user_name;
    private String password;
    private String first_name;
    private String last_name;
    private String email;
    private RoleDTO roleDTO;
    private CustomerDTO customerDTO;

    public UserDTO(int user_id, String user_name, String password, String first_name, String last_name, RoleDTO roleDTO) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.roleDTO = roleDTO;
        this.customerDTO = customerDTO;
    }

    public UserDTO(int user_id, String user_name, String password, String first_name,
                   String last_name, String email, RoleDTO roleDTO,CustomerDTO customerDTO) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.roleDTO = roleDTO;
        this.customerDTO = customerDTO;
    }

    public UserDTO(String user_name, String password, String first_name, String last_name, String email, RoleDTO roleDTO,CustomerDTO customerDTO) {
        this.user_name = user_name;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.roleDTO = roleDTO;
        this.customerDTO = customerDTO;
    }

    public UserDTO toDTO(User user) {

        RoleDTO roleDTO = new RoleDTO().toDTO(user.getRole());
        UserDTO userDTO;
        if(user.getCustomer()==null){
            userDTO= new UserDTO(user.getUser_id(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getFirst_name(),
                    user.getLast_name(),
                    user.getEmail(),
                    roleDTO,
                    null
            );
        }else {
            userDTO = new UserDTO(user.getUser_id(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getFirst_name(),
                    user.getLast_name(),
                    user.getEmail(),
                    roleDTO,
                    new CustomerDTO().toDTO(user.getCustomer())
            );
        }

        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {

        User user = new User(userDTO.getUser_id(), userDTO.getUser_name(), userDTO.getPassword(),
                userDTO.getFirst_name(), userDTO.getLast_name(),userDTO.getEmail(),
                new RoleDTO().toEntity(userDTO.getRoleDTO()),
                new CustomerDTO().toEntity(userDTO.getCustomerDTO()));
        return user;
    }

    public List<UserDTO> toListDTO(List<User> listUser) {
        List<UserDTO> listUserDTO = new ArrayList<>();
        listUser.forEach(x -> {
            listUserDTO.add(new UserDTO(x.getUser_id(),
                    x.getUsername(),
                    x.getPassword(),
                    x.getFirst_name(),
                    x.getLast_name(),
                    x.getEmail(),
                    new RoleDTO().toDTO(x.getRole()),
                    new CustomerDTO().toDTO(x.getCustomer())
            ));
        });
        return listUserDTO;

    }
}
