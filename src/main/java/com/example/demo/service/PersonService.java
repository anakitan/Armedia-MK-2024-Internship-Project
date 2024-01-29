package com.example.demo.service;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Optional<Person> createPerson(Person person);

    List<Person> listAllPersons();

    Person getPersonById(Long id);

    List<Person> findByEmail(String email);

    List<Person> findByStreetAddress(String streetAddress);

    Optional<Person> getPersonDetails(Long personId);

    Optional<Person> addPersonAddress(Long personId, PostalAddress newAddress);

    Optional<Person> addPersonContactMethod(Long personId, ContactMethod contactMethod);
}
