package com.example.demo.service.impl;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.models.exceptions.UserAlreadyExistsException;
import com.example.demo.repository.dao.PersonDao;
import com.example.demo.models.exceptions.PersonNotFoundException;
import com.example.demo.service.PersonService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    @Transactional
    public Optional<Person> createPerson(Person person) {
        return personDao.createPerson(person);
    }

    @Override
    public List<Person> listAllPersons() {
        return this.personDao.listAllPersons();
    }

    @Override
    public Person getPersonById(Long id) {
        try {
            return this.personDao.getPersonById(id);
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(String.format("Person with id: %d was not found.", id));
        }
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        try {
            return this.personDao.findByEmail(email);
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(String.format("Person with email: %s was not found.", email));
        }
    }

    @Override
    public Optional<Person> findByStreetAddress(String streetAddress) {
        try {
            return this.personDao.findByStreetAddress(streetAddress);
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(String.format("Person with street address: %s was not found.", streetAddress));
        }
    }

    @Override
    public Optional<Person> getPersonDetails(Long personId) {
        try {
            return Optional.ofNullable(this.personDao.getPersonById(personId));
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(String.format("Person with id: %d was not found.", personId));
        }
    }

    @Override
    @Transactional
    public Optional<Person> addPersonAddress(Long personId, PostalAddress newAddress) {
        try {
            return personDao.addPersonAddress(personId, newAddress);
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(String.format("Person with id: %d was not found.", personId));
        }
    }

    @Override
    @Transactional
    public Optional<Person> addPersonContactMethod(Long personId, ContactMethod contactMethod) {
        try {
            return this.personDao.addPersonContactMethod(personId, contactMethod);
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(String.format("Person with id: %d was not found.", personId));
        }
    }
}
