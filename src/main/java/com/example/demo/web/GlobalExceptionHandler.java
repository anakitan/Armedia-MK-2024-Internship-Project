package com.example.demo.web;

import com.example.demo.models.exceptions.EmailNotFoundException;
import com.example.demo.models.exceptions.PersonNotFoundException;
import com.example.demo.models.exceptions.PostalAddressAlreadyExistsException;
import com.example.demo.models.exceptions.StreetAddressNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> handleValidationException(final MethodArgumentNotValidException ex) {

        List<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {PersonNotFoundException.class, EmailNotFoundException.class, StreetAddressNotFoundException.class})
    @ResponseBody
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostalAddressAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<Object> handleAlreadyExistsException(PostalAddressAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
