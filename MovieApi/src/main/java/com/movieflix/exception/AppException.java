package com.movieflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppException {

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(IOException.class)
    public Map<String,String> fileStorageException(IOException ex){
        Map<String,String> map = new HashMap<>();
        map.put("Error message : ",ex.getMessage());
        return map;
    }
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(FileNotFoundException.class)
    public Map<String,String> fileNotFoundException(IOException ex){
        Map<String,String> map = new HashMap<>();
        map.put("Error message : ",ex.getMessage());
        return map;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MovieNotFoundException.class)
    public Map<String,String> fileNotFoundException(MovieNotFoundException ex){
        Map<String,String> map = new HashMap<>();
        map.put("Error message : ",ex.getMessage());
        return map;
    }
}
