package com.example.demo.web;

import com.example.demo.models.*;
import com.example.demo.service.impl.PersonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonServiceImpl personService;

    public PersonController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @Validated
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Optional<Person>> createPerson(@Valid @RequestBody Person person) {
        logger.info("Starting createPerson method with info log level");
        return new ResponseEntity<>(this.personService.createPerson(person), HttpStatus.OK);
    }

    @GetMapping("/listAll")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<Person> listAllPersons() {
        logger.info("Starting listAllPersons method with info log level");
        return this.personService.listAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        logger.info("Starting getPersonById method with info log level");
        return new ResponseEntity<>(this.personService.getPersonById(id), HttpStatus.OK);
    }

    @GetMapping("/byEmail")
    public ResponseEntity<?> getPersonByEmail(@RequestParam String email) {
        logger.info("Starting getPersonByEmail method with info log level");
        return new ResponseEntity<>(this.personService.findByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/byStreetAddress")
    public ResponseEntity<?> getPersonByStreetAddress(@RequestParam String streetAddress) {
        logger.info("Starting getPersonByStreetAddress method with info log level");
        return new ResponseEntity<>(this.personService.findByStreetAddress(streetAddress), HttpStatus.OK);
    }

    @GetMapping("/{personId}/details")
    public ResponseEntity<Person> getPersonDetails(@PathVariable Long personId) {
        logger.info("Starting getPersonDetails method with info log level");
        return new ResponseEntity<>(this.personService.getPersonById(personId), HttpStatus.OK);
    }

    @PutMapping("/{personId}/addAddress")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Optional<Person>> addPersonAddress(@PathVariable Long personId, @RequestBody PostalAddress newAddress) {
        logger.info("Starting addPersonAddress method with info log level");
        return new ResponseEntity<>(this.personService.addPersonAddress(personId, newAddress), HttpStatus.OK);
    }

    @PutMapping("/{personId}/addContact")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Optional<Person>> addPersonContactMethod(@PathVariable Long personId, @Valid @RequestBody ContactMethod newContactMethod) {
        logger.info("Starting addPersonContactMethod method with info log level");
        return new ResponseEntity<>(this.personService.addPersonContactMethod(personId, newContactMethod), HttpStatus.OK);
    }
}
