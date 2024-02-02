package com.example.demo.web;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.service.impl.PersonServiceDaoImpl;
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

    private final PersonServiceDaoImpl personServiceDao;

    public PersonController(PersonServiceDaoImpl personServiceDao) {
        this.personServiceDao = personServiceDao;
    }

    @Validated
    @PostMapping("/create")
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        return this.personServiceDao.createPerson(person)
                .map(p -> ResponseEntity.ok().body(p))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Person>> listAllPersons() {
        List<Person> persons = this.personServiceDao.listAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = this.personServiceDao.getPersonById(id);
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byEmail")
    public ResponseEntity<?> getPersonByEmail(@RequestParam String email) {
        return new ResponseEntity<>(this.personServiceDao.findByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/byStreetAddress")
    public ResponseEntity<?> getPersonByStreetAddress(@RequestParam String streetAddress) {
        return new ResponseEntity<>(this.personServiceDao.findByStreetAddress(streetAddress), HttpStatus.OK);
    }

    @GetMapping("/{personId}/details")
    public ResponseEntity<Person> getPersonDetails(@PathVariable Long personId) {
        Optional<Person> optionalPerson = this.personServiceDao.getPersonDetails(personId);

        return optionalPerson.map(
                        person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{personId}/addAddress")
    public ResponseEntity<Person> addPersonAddress(@PathVariable Long personId, @RequestBody PostalAddress newAddress) {
        Optional<Person> updatedPerson = this.personServiceDao.addPersonAddress(personId, newAddress);

        return updatedPerson.map(person -> ResponseEntity.ok().body(person))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{personId}/addContact")
    public ResponseEntity<Person> addPersonContactMethod(@PathVariable Long personId, @Valid @RequestBody ContactMethod newContactMethod) {
        Optional<Person> updatedPerson = this.personServiceDao.addPersonContactMethod(personId, newContactMethod);

        return updatedPerson.map(person -> ResponseEntity.ok().body(person))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
