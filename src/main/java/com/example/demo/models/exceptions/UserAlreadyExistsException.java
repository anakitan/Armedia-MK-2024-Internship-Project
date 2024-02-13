package com.example.demo.models.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException() {
        super(String.format("User with these credentials already exists."));
    }
}
