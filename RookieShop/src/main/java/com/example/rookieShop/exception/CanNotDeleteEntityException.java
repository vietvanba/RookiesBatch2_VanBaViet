package com.example.RookieShop.exception;

public class CanNotDeleteEntityException extends RuntimeException{
    public CanNotDeleteEntityException(String err,Exception e)
    {
        super(err + '\n'+e.getMessage());
    }
}
