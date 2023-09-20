package com.example.RookieShop.exception;

public class CanNotConvertException extends RuntimeException{
    public CanNotConvertException(String err, Exception e)
    {
        super(err + '\n'+e.getMessage());
    }
}
