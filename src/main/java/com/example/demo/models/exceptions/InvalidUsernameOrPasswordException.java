package com.example.demo.models.exceptions;

public class InvalidUsernameOrPasswordException extends RuntimeException{
    public InvalidUsernameOrPasswordException() {
        super(String.format("Authentication failed. Invalid username or password."));
    }
}
