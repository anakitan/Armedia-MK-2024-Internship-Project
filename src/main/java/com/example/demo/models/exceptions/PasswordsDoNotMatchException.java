package com.example.demo.models.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException{

    public PasswordsDoNotMatchException() {
        super("Passwords do not match exception.");
    }
}
