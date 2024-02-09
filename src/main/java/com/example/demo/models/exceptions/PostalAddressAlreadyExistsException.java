package com.example.demo.models.exceptions;

public class PostalAddressAlreadyExistsException extends RuntimeException{
    public PostalAddressAlreadyExistsException() {
        super("Postal address: %s already exists.");
    }
}
