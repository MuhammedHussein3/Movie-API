package com.movieflix.exception;

import java.io.IOException;

public class FileNameExistException extends IOException {

    public FileNameExistException(String message) {
        super(message);
    }
}
