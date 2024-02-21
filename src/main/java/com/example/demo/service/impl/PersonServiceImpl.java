package com.example.demo.service.impl;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.repository.dao.PersonDao;
import com.example.demo.models.exceptions.PersonNotFoundException;
import com.example.demo.service.PersonService;
import com.example.demo.web.PersonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonDao personDao;

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    @Transactional
    public Optional<Person> createPerson(Person person) {
        logger.info("createPerson method in service class started");
        return this.personDao.createPerson(person);
    }

    @Override
    public List<Person> listAllPersons() {
        try {
            logger.info("listAllPersons method in service class started");
            return this.personDao.listAllPersons();
        } catch (NoResultException ex) {
            logger.error("Exception in listAllPersons of service class");
            throw new PersonNotFoundException(String.format("The list of persons was not found"));
        }
    }

    @Override
    public Person getPersonById(Long id) {
        try {
            logger.info("getPersonById method in service class started");
            return this.personDao.getPersonById(id);
        } catch (NoResultException ex) {
            logger.error("Exception in getPersonById of service class");
            throw new PersonNotFoundException(String.format("Person with id: %d was not found.", id));
        }
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        try {
            logger.info("findByEmail method in service class started");
            return this.personDao.findByEmail(email);
        } catch (NoResultException ex) {
            logger.error("Exception in findByEmail of service class");
            throw new PersonNotFoundException(String.format("Person with email: %s was not found.", email));
        }
    }

    @Override
    public Optional<Person> findByStreetAddress(String streetAddress) {
        try {
            logger.info("findByStreetAddress method in service class started");
            return this.personDao.findByStreetAddress(streetAddress);
        } catch (NoResultException ex) {
            logger.error("Exception in findByStreetAddress of service class");
            throw new PersonNotFoundException(String.format("Person with street address: %s was not found.", streetAddress));
        }
    }

    @Override
    public Optional<Person> getPersonDetails(Long personId) {
        try {
            logger.info("getPersonDetails method in service class started");
            return Optional.ofNullable(this.personDao.getPersonById(personId));
        } catch (NoResultException ex) {
            logger.error("Exception in getPersonDetails of service class");
            throw new PersonNotFoundException(String.format("Person with id: %d was not found.", personId));
        }
    }

    @Override
    @Transactional
    public Optional<Person> addPersonAddress(Long personId, PostalAddress newAddress) {
        logger.info("addPersonAddress method in service class started");
        return Optional.ofNullable(this.personDao.addPersonAddress(personId, newAddress)
                .orElseThrow(() -> new PersonNotFoundException(String.format("Person with id: %d was not found.", personId))));
    }

    @Override
    @Transactional
    public Optional<Person> addPersonContactMethod(Long personId, ContactMethod contactMethod) {
        logger.info("addPersonContactMethod method in service class started");
        return Optional.ofNullable(this.personDao.addPersonContactMethod(personId, contactMethod)
                .orElseThrow(() -> new PersonNotFoundException(String.format("Person with id: %d was not found.", personId))));
    }
}
