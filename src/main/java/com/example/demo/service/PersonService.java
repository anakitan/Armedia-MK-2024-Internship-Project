package com.example.demo.service;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.repository.dao.PersonDaoImpl;
import com.example.demo.models.exceptions.EmailNotFoundException;
import com.example.demo.models.exceptions.PersonNotFoundException;
import com.example.demo.models.exceptions.StreetAddressNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonDaoImpl personDao;

    public PersonService(PersonDaoImpl personDao) {
        this.personDao = personDao;
    }

    @Transactional
    public Optional<Person> createPerson(Person person) {
        return personDao.createPerson(person);
    }

    public List<Person> listAllPersons() {
        return this.personDao.listAllPersons();
    }

    public Person getPersonById(Long id) {
        try {
            return this.personDao.getPersonById(id);
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(id);
        }
    }

    public Optional<Person> findByEmail(String email) {
        try {
            return this.personDao.findByEmail(email);
        } catch (RuntimeException ex) {
            throw new EmailNotFoundException(email);
        }
    }

    public Optional<Person> findByStreetAddress(String streetAddress) {
        try {
            return this.personDao.findByStreetAddress(streetAddress);
        } catch (RuntimeException ex) {
            throw new StreetAddressNotFoundException(streetAddress);
        }
    }

    public Optional<Person> getPersonDetails(Long personId) {
        try {
            return Optional.ofNullable(this.personDao.getPersonById(personId));
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(personId);
        }
    }

    @Transactional
    public Optional<Person> addPersonAddress(Long personId, PostalAddress newAddress) {
        try {
            return this.personDao.addPersonAddress(personId, newAddress);
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(personId);
        }
    }

    @Transactional
    public Optional<Person> addPersonContactMethod(Long personId, ContactMethod contactMethod) {
        try {
            return this.personDao.addPersonContactMethod(personId, contactMethod);
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(personId);
        }
    }
}
