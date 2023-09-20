package com.example.RookieShop.payload.request;

import com.example.RookieShop.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignupRequest {
    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "Username must not be blank")
    @Size(max = 50,message = "Username must be less than 50 characters")
    @Email
    private String email;


    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 40,message = "Usernames can only be between 6 and 40 characters")
    private String password;

    @NotBlank(message = "First name must not be blank")
    @Size(max = 50,message = "First name must be less than 50 characters")
    private String first_name;
    @NotBlank(message = "Last name must not be blank")
    @Size(max = 50,message = "Last name must be less than 50 characters")
    private String last_name;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}