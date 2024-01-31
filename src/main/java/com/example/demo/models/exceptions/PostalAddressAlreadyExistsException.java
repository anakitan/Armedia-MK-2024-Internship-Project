package com.example.demo.models.exceptions;

import com.example.demo.models.PostalAddress;

public class PostalAddressAlreadyExistsException extends RuntimeException{
    public PostalAddressAlreadyExistsException(PostalAddress streetAddress) {
        super(String.format("Postal address: %s already exists", streetAddress.toString()));
    }
}
