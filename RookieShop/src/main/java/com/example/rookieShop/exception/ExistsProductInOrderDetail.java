package com.example.RookieShop.exception;

public class ExistsProductInOrderDetail extends RuntimeException{
    public ExistsProductInOrderDetail(String err)
    {
        super(err);
    }
}
