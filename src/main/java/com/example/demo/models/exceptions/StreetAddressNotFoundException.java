package com.example.demo.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StreetAddressNotFoundException extends RuntimeException {
    public StreetAddressNotFoundException(String streetAddress) {
        super(String.format("Person with street address: %s was not found.", streetAddress));
    }
}
