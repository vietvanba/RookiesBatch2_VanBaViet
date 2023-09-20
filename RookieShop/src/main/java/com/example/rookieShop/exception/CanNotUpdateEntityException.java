package com.example.RookieShop.exception;

public class CanNotUpdateEntityException extends RuntimeException{
    public CanNotUpdateEntityException(String err)
    {
        super(err);
    }
    public CanNotUpdateEntityException(String err, Exception e)
    {
        super(err + '\n'+e.getMessage());
    }
}
