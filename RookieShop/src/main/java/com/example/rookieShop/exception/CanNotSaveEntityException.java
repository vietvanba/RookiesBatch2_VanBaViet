package com.example.RookieShop.exception;

public class CanNotSaveEntityException extends RuntimeException{
    public CanNotSaveEntityException(String err,Exception e)
    {
        super(err + '\n'+e.getMessage());
    }
    public CanNotSaveEntityException(String err)
    {
        super(err);
    }
}
