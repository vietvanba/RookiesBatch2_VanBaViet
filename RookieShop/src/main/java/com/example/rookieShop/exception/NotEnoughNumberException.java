package com.example.RookieShop.exception;

public class NotEnoughNumberException extends RuntimeException{
    public NotEnoughNumberException(String err, Exception e)
    {
        super(err+" " + e.getMessage());
    }
    public NotEnoughNumberException(String err)
    {
        super(err);
    }
}
