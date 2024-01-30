package com.example.demo.models.exceptions;

import com.example.demo.models.ContactMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super(String.format("Person with email: %s was not found.", email));
    }
}
