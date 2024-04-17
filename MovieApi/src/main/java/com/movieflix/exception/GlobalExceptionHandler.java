package com.movieflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
    public Map<String,String> movieNotFoundException(MovieNotFoundException ex){
        Map<String,String> map = new HashMap<>();
        map.put("Error message : ",ex.getMessage());
        return map;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNameExistException.class)
    public Map<String,String> fileNameExistException(FileNameExistException ex){
        Map<String,String> map = new HashMap<>();
        map.put("Error message : ",ex.getMessage());
        return map;
    }
    @ExceptionHandler(EmptyFileException.class)
    public ProblemDetail handleEmptyFileException(EmptyFileException ex){

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
    }
    @ExceptionHandler(PageSizeException.class)
    public ProblemDetail handlePageSizeException(PageSizeException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(Exception ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());
    }
    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ProblemDetail handleRefreshTokenNotFoundException(RefreshTokenNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(OtpInvalidException.class)
    public Map<String,String> otpInvalidException(OtpInvalidException ex){

        Map<String,String> map = new HashMap<>();
        map.put("Error message : ",ex.getMessage());
        return map;
    }
}
