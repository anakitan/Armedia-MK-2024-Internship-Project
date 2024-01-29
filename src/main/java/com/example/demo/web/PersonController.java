package com.example.demo.web;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Validated
    @PostMapping("/create")
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        return this.personService.createPerson(person)
                .map(p -> ResponseEntity.ok().body(p))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Person>> listAllPersons() {
        List<Person> persons = this.personService.listAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = this.personService.getPersonById(id);
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byEmail")
    public ResponseEntity<List<Person>> getPersonByEmail(@RequestParam String email) {
        List<Person> persons = this.personService.findByEmail(email);
        return ResponseEntity.ok().body(persons);
    }

    @GetMapping("/byStreetAddress")
    public ResponseEntity<List<Person>> getPersonByStreetAddress(@RequestParam String streetAddress) {
        List<Person> persons = this.personService.findByStreetAddress(streetAddress);
        return ResponseEntity.ok().body(persons);
    }

    @GetMapping("/{personId}/details")
    public ResponseEntity<Person> getPersonDetails(@PathVariable Long personId) {
        Optional<Person> optionalPerson = this.personService.getPersonDetails(personId);

        return optionalPerson.map(
                        person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{personId}/addAddress")
    public ResponseEntity<Person> addPersonAddress(@PathVariable Long personId, @RequestBody PostalAddress newAddress) {
        Optional<Person> updatedPerson = this.personService.addPersonAddress(personId, newAddress);

        return updatedPerson.map(person -> ResponseEntity.ok().body(person))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{personId}/addContact")
    public ResponseEntity<Person> addPersonContactMethod(@PathVariable Long personId, @Valid @RequestBody ContactMethod newContactMethod) {
        Optional<Person> updatedPerson = this.personService.addPersonContactMethod(personId, newContactMethod);

        return updatedPerson.map(person -> ResponseEntity.ok().body(person))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
