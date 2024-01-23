package com.example.demo.service;

import com.example.demo.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Person createPerson(Person person);

    List<Person> listAllPersons();

    Person getPersonById(Long id);

    List<Person> findByEmail(String email);

    List<Person> findByStreetAddress(String streetAddress);
}
