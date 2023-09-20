package com.example.RookieShop.service;

import com.example.RookieShop.dto.UserDTO;
import com.example.RookieShop.exception.CanNotDeleteEntityException;
import com.example.RookieShop.exception.CanNotSaveEntityException;
import com.example.RookieShop.exception.CanNotUpdateEntityException;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.User;
import com.example.RookieShop.repository.RoleRepository;
import com.example.RookieShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Component
public class UserService {
    @Autowired
    UserRepository repository;
    @Autowired
    RoleRepository roleRepository;


    public User save(User user) {
        try {
            user.setRole(roleRepository.getByRoleName("ROLE_USER"));
            return repository.save(user);
        } catch (Exception e) {
            throw new CanNotSaveEntityException("Can't save user to database.Please " +
                    "check again", e);
        }

    }

    public User saveAdmin(User user) {
        try {
            user.setRole(roleRepository.getByRoleName("ROLE_ADMIN"));
            if(repository.existsByEmail(user.getEmail()))
                throw new CanNotSaveEntityException("Email is exist in database");
            if(repository.existsByUsername(user.getUsername()))
                throw new CanNotSaveEntityException("Username is exist in database");
            return repository.save(user);
        } catch (Exception e) {
            throw new CanNotSaveEntityException("Can't save user to database.Please " +
                    "check again", e);
        }

    }

    public User getByID(int id) {
        User user = repository.findById(id).orElse(null);
        if(user==null)
            throw new NotFoundEntityException("Can't not found the user with id" + id);
        return user;
    }

    public List<User> getAll() {
        List<User> list = repository.findAll();
        if (list.size() == 0)
            throw new NotFoundEntityException("Can't get any user");
        return list;

    }

    public String deleteById(int id) {
        User user = repository.findById(id).orElse(null);
        if (user == null)
            throw new NotFoundEntityException("Not found user with id: " + id);
        try {
            repository.delete(user);
            return "Delete successful";
        } catch (Exception e) {
            throw new CanNotDeleteEntityException("Can't delete user with id " + id, e);
        }

    }

    public User udpateById(UserDTO userDTO, int id) {
        User u = repository.findById(id).orElse(null);
        if (u == null)
            throw new NotFoundEntityException("Can't not found the user with id" + id);

        try {
            if (userDTO.getUser_name() != null) {
                if (!u.getUsername().equals(userDTO.getUser_name()))
                    u.setUsername(userDTO.getUser_name());
            }
            if (userDTO.getPassword() != null) {
                if (!u.getPassword().equals(userDTO.getPassword()))
                    u.setPassword(userDTO.getPassword());
            }
            if (userDTO.getFirst_name() != null) {
                if (!u.getFirst_name().equals(userDTO.getFirst_name()))
                    u.setFirst_name(userDTO.getFirst_name());
            }
            if (userDTO.getLast_name() != null) {
                if (!u.getLast_name().equals(userDTO.getLast_name()))
                    u.setLast_name(userDTO.getLast_name());
            }

            return repository.save(u);
        } catch (Exception e) {
            throw new CanNotUpdateEntityException("Can't update user with id " + id, e);
        }


    }

}
