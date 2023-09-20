package com.example.RookieShop.service;

import com.example.RookieShop.dto.RoleDTO;
import com.example.RookieShop.exception.CanNotDeleteEntityException;
import com.example.RookieShop.exception.CanNotSaveEntityException;
import com.example.RookieShop.exception.CanNotUpdateEntityException;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.Role;
import com.example.RookieShop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Component
public class RoleService {

    @Autowired
    RoleRepository repository;


    public Role save(Role role) {
        try {
            return repository.save(role);
        } catch (Exception e) {
            throw new CanNotSaveEntityException("Can't save role to database.Please " +
                    "check again", e);
        }

    }

    public Role getByID(int id) {
        Role role = repository.findById(id).orElse(null);
        if (role == null)
            throw new NotFoundEntityException("Can't not found the role with id" + id);
        return repository.getById(id);
    }

    public List<Role> getAll() {
        List<Role> list = repository.findAll();
        if (list.size() == 0)
            throw new NotFoundEntityException("Not found any role");
        return list;

    }

    public String deleteById(int id) {
        Role r = repository.findById(id).orElse(null);
        if (r == null)
            throw new NotFoundEntityException("Not found role with id:" + id);
        try {
            repository.delete(r);
            return "Delete successful";
        } catch (Exception e) {
            throw new CanNotDeleteEntityException("Can't delete role with id " + id, e);
        }
    }

    public Role udpateById(RoleDTO roleDTO, int id) {
        Role r = repository.findById(id).orElse(null);
        if (r == null)
            throw new NotFoundEntityException("Not found role with id:" + id);
        try {
            if (!r.getRole_name().equals(roleDTO.getRole_name()) && r.getRole_name() != null) {
                r.setRole_name(roleDTO.getRole_name());
            }
            return repository.save(r);
        } catch (Exception e) {
            throw new CanNotUpdateEntityException("Can't update role with id " + id, e);
        }
    }
}
