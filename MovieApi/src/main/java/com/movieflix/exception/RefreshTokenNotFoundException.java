package com.movieflix.exception;

public class RefreshTokenNotFoundException extends Exception{
    public RefreshTokenNotFoundException(String msg){
        super(msg);
    }
}
