package com.example.demo.web;

import com.example.demo.models.*;
import com.example.demo.service.impl.PersonServiceImpl;
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

    private final PersonServiceImpl personService;

    public PersonController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @Validated
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Optional<Person>> createPerson(@Valid @RequestBody Person person) {
        return new ResponseEntity<>(this.personService.createPerson(person), HttpStatus.OK);
    }

    @GetMapping("/listAll")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<Person> listAllPersons() {
        return this.personService.listAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        return new ResponseEntity<>(this.personService.getPersonById(id), HttpStatus.OK);
    }

    @GetMapping("/byEmail")
    public ResponseEntity<?> getPersonByEmail(@RequestParam String email) {
        return new ResponseEntity<>(this.personService.findByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/byStreetAddress")
    public ResponseEntity<?> getPersonByStreetAddress(@RequestParam String streetAddress) {
        return new ResponseEntity<>(this.personService.findByStreetAddress(streetAddress), HttpStatus.OK);
    }

    @GetMapping("/{personId}/details")
    public ResponseEntity<Person> getPersonDetails(@PathVariable Long personId) {
        return new ResponseEntity<>(this.personService.getPersonById(personId), HttpStatus.OK);
    }

    @PutMapping("/{personId}/addAddress")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Optional<Person>> addPersonAddress(@PathVariable Long personId, @RequestBody PostalAddress newAddress) {
        return new ResponseEntity<>(this.personService.addPersonAddress(personId, newAddress), HttpStatus.OK);
    }

    @PutMapping("/{personId}/addContact")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Optional<Person>> addPersonContactMethod(@PathVariable Long personId, @Valid @RequestBody ContactMethod newContactMethod) {
        return new ResponseEntity<>(this.personService.addPersonContactMethod(personId, newContactMethod), HttpStatus.OK);
    }
}
