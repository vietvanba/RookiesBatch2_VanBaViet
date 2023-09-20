package com.example.RookieShop.service;

import com.example.RookieShop.dto.CustomerDTO;
import com.example.RookieShop.dto.RoleDTO;
import com.example.RookieShop.dto.UserDTO;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.*;
import com.example.RookieShop.repository.RoleRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    UserService userService;
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private List<User> list;
    private User user1;
    private User user2;
    private Role role;
    private Customer cus;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        this.list = new ArrayList<>();
        user1 = new User("username1", "password", "first_name", "last_name", "email");
        user2 = new User("username2", "password", "first_name", "last_name", "email");
        list.add(user1);
        list.add(user2);
        role = new Role(1, "user");
        cus= new Customer();
        userDTO = new UserDTO("name1", "12312", "12312", "vit", "baviet19@gmail" +
                ".com",
                new RoleDTO().toDTO(this.role),new CustomerDTO().toDTO(this.cus));
    }

    @Test
    public void save_User_IfSuccess() {
        when(userRepository.save(any(User.class))).thenReturn(user1);
        when(roleRepository.getByRoleName(anyString())).thenReturn(role);
        userRepository.save(user1);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void save_UserAdmin_IfSuccess() {
        when(userRepository.save(any(User.class))).thenReturn(user1);
        when(roleRepository.getByRoleName(anyString())).thenReturn(role);
        userRepository.save(user1);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void getAll_ReturnListUser_IfFound() {
        Mockito.when(userRepository.findAll()).thenReturn(list);
        List<User> result = userService.getAll();
        assertEquals(result.size(), 2);
    }

    @Test
    public void getByID_ReturnUser_IfFound() {
        Mockito.when(userRepository.findById(user1.getUser_id())).thenReturn(Optional.ofNullable(user1));
        User user = userService.getByID(user1.getUser_id());
        assertEquals(user, user1);
    }

    @Test
    public void getByID_ReturnException_IfNotFound() {
        BDDMockito.given(userRepository.findById(user1.getUser_id())).willReturn(Optional.ofNullable(null));
        try {
            userService.getByID(user1.getUser_id());
        } catch (NotFoundEntityException e) {
            exception.expect(NotFoundEntityException.class);
        }
    }

    @Test
    public void deleteById_User_IfSuccess() {
        when(userRepository.findById(user1.getUser_id())).thenReturn(Optional.ofNullable(user1));
        userService.deleteById(user1.getUser_id());
        verify(userRepository).delete(user1);
    }

    @Test
    public void deleteById_User_IfNotSuccess() {
        BDDMockito.given(userRepository.findById(user1.getUser_id())).willReturn(Optional.ofNullable(null));

        try {
            userService.deleteById(user1.getUser_id());
        } catch (NotFoundEntityException e) {
            exception.expect(NotFoundEntityException.class);
        }
    }

    @Test
    public void updateProduct_IfSuccess() {
        when(userRepository.findById(user1.getUser_id())).thenReturn(Optional.ofNullable(user1));
        User user = user1;
        user.setFirst_name(userDTO.getFirst_name());
        user.setLast_name(userDTO.getLast_name());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUser_name());
        user.setEmail(userDTO.getEmail());
        user.setRole(new RoleDTO().toEntity(userDTO.getRoleDTO()));
        userService.udpateById(userDTO, user1.getUser_id());
        assertEquals(user, user1);
    }

}
