package com.movieflix.exception;

public class PageSizeException extends IllegalAccessException{

    public PageSizeException(String message){
        super(message);
    }
}
