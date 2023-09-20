package com.example.RookieShop.exception;

public class NotFoundEntityException extends RuntimeException{
    public NotFoundEntityException(String err,Exception e){
        super(err + '\n'+e.getMessage());

    }
    public NotFoundEntityException(String err){
        super(err);

    }

}
