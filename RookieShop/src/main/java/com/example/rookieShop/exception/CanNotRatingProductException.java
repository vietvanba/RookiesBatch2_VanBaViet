package com.example.RookieShop.exception;

public class CanNotRatingProductException extends RuntimeException{
    public CanNotRatingProductException(String err)
    {
        super(err);
    }
}
