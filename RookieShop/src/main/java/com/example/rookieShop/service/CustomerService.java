package com.example.RookieShop.service;

import com.example.RookieShop.dto.CustomerDTO;
import com.example.RookieShop.exception.CanNotDeleteEntityException;
import com.example.RookieShop.exception.CanNotSaveEntityException;
import com.example.RookieShop.exception.CanNotUpdateEntityException;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.Category;
import com.example.RookieShop.model.Customer;
import com.example.RookieShop.model.User;
import com.example.RookieShop.repository.CustomerRepository;
import com.example.RookieShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageUploader imageUploader;
    String avatar="https://res.cloudinary.com/rookies-app-batch2/image/upload" +
            "/v1627042158/143086968_2856368904622192_1959732218791162458_n.png_lvl1y7.png";
    public Customer save(Customer customer, int user_id) {

        try {

            customer.setUser(userRepository.getById(user_id));
            customer.setAvatar(imageUploader.uploadImage(customer.getAvatar()));
            return repository.save(customer);
        } catch (Exception e) {
            throw new CanNotSaveEntityException("Can't save Customer with UserID : " + user_id, e);
        }
    }
    public Customer updateAvatar(String avatar,int id)
    {
        Customer customer=repository.findById(id).orElse(null);
        User user =userRepository.findById(id).orElse(null);

        if(user==null)
        {
            throw new NotFoundEntityException("Not found user with id: "+id);
        }
        if(customer!=null){
            customer.setAvatar(imageUploader.uploadImage(avatar));
            return repository.save(customer);
        }
            else
        {
            throw new NotFoundEntityException("Not found customer with id: "+id);
        }
    }
    public Customer getByID(int id) {
        Customer customer=repository.findById(id).orElse(null);

            return customer;

    }

    public List<Customer> getAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new NotFoundEntityException("Can't get any Customer", e);

        }

    }

    public String deleteByUserId(int id) {
        try {
            Customer customer = repository.findByUserId(id).get();
            repository.delete(customer);
            return "Delete successful";
        } catch (Exception e) {
            throw new CanNotDeleteEntityException("Can't delete Customer with id " + id, e);
        }

    }

    public Customer udpateById(CustomerDTO customerDTO, int user_id) {
        try {
            Customer customer = repository.getById(user_id);
            if (customerDTO.getPhone() != null)
                if (!customerDTO.getPhone().equals(customer.getPhone()))
                    customer.setPhone(customerDTO.getPhone());
            if (customerDTO.getAvatar() != null)
                if (!customerDTO.getAvatar().equals(avatar))
                    customer.setAvatar(imageUploader.uploadImage(customerDTO.getAvatar()));
            if (customerDTO.getAddress() != null)
                if (!customerDTO.getAddress().equals(customer.getAddress()))
                    customer.setAddress(customerDTO.getAddress());
            try {
                return repository.save(customer);
            } catch (Exception e) {
                throw new CanNotUpdateEntityException("Can't update Customer with User_id: " + user_id, e);
            }
        } catch (Exception e) {
            throw new NotFoundEntityException("Can't not found the Customer with " +
                    "User_id: " + user_id
                    , e);
        }


    }
}
