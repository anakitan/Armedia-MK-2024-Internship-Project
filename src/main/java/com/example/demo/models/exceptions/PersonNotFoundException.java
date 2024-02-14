package com.example.demo.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String message) {
        super(String.format(message));
    }
}
