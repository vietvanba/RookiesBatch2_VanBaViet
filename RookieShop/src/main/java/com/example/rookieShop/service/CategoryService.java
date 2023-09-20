package com.example.RookieShop.service;

import com.example.RookieShop.dto.CategoryDTO;
import com.example.RookieShop.exception.CanNotDeleteEntityException;
import com.example.RookieShop.exception.CanNotSaveEntityException;
import com.example.RookieShop.exception.CanNotUpdateEntityException;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.Category;
import com.example.RookieShop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Component
public class CategoryService {

    @Autowired
    CategoryRepository repository;


    public Category save(Category categories) {
        try {
            return repository.save(categories);
        } catch (Exception e) {
            throw new CanNotSaveEntityException("Can't save Category to database.Please " +
                    "check again", e);
        }

    }

    public Category getByID(int id) {

        Category category =repository.findById(id).orElse(null);
        return category;

    }

    public List<Category> getAll() {
        List<Category> list=repository.findAll();
        if (list.size()!=0)
        {
            return list;
        }else
        {
            throw new NotFoundEntityException("Not found any entity");
        }

    }

    public String deleteById(int id) {
        Category category = repository.findById(id).orElse(null);
        if(category==null)
        {
            throw new NotFoundEntityException("Not found entity with id: "+id);
        }
        try {
            repository.delete(category);
            return "Delete successful";
        } catch (Exception e) {
            throw new CanNotDeleteEntityException("Can't delete Category with id " + id, e);
        }
    }

    public Category udpateById(CategoryDTO categoryDTO, int id) {

        try {
            Category c = repository.getById(id);
            if (categoryDTO.getName() != null)
                if (!c.getName().equals(categoryDTO.getName()))
                    c.setName(categoryDTO.getName());
            if (categoryDTO.getDescription() != null)
                if (!c.getDescription().equals(categoryDTO.getDescription()))
                    c.setDescription(categoryDTO.getDescription());
            repository.save(c);
            return c;
        } catch (Exception e) {
            throw new CanNotUpdateEntityException("Can't update Category with id " + id, e);
        }
    }

}
