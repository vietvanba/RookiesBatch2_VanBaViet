package com.example.RookieShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,Object> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String,Object> map = new HashMap<>();
        Map<String,String> errors = new HashMap<>();
        ex.getAllErrors().forEach(x->{
            String fieldName = ((FieldError) x).getField();
            String errorMessage = x.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        map.put("message",errors);
        return map;
    }
    @ExceptionHandler(NotFoundEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse hanlderNotFoundEntityException(NotFoundEntityException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    @ExceptionHandler(NotEnoughNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse hanlderNotEnoughNumberException(NotEnoughNumberException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(CanNotSaveEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse hanlderCanNotSaveEntityException(CanNotSaveEntityException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(CanNotUpdateEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse hanlderCanNotUpdateEntityException(CanNotUpdateEntityException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(CanNotDeleteEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse hanlderCanNotDeleteEntityException(CanNotDeleteEntityException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    @ExceptionHandler(ExistsProductInOrderDetail.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse hanlderExistsProductInOrderDetail(ExistsProductInOrderDetail ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(CanNotRatingProductException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse hanlderCanNotRatingProductException(CanNotRatingProductException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(CanNotConvertException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse hanlderCanNotConvertException(CanNotConvertException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
