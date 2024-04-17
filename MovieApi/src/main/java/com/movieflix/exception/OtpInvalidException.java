package com.movieflix.exception;

public class OtpInvalidException extends RuntimeException{

    public OtpInvalidException(String mess){
        super(mess);
    }
}
