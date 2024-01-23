package com.example.demo.web;

import com.example.demo.models.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/create")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = this.personService.createPerson(person);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
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
            return new ResponseEntity<>(person, HttpStatus.NOT_FOUND);
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
}
