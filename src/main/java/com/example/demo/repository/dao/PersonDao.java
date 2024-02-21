package com.example.demo.repository.dao;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.models.exceptions.UserAlreadyExistsException;
import com.example.demo.web.PersonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    @PersistenceContext
    private final EntityManager entityManager;

    public PersonDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Person> createPerson(Person person) {
        try {
            entityManager.persist(person);
            return Optional.of(person);
        } catch (PersistenceException ex) {
            throw new UserAlreadyExistsException("Duplicate entry for contact method or postal address.");
        }
    }

    public List<Person> listAllPersons() {
        return entityManager.createNamedQuery("Person.findAll", Person.class).getResultList();
    }

    public Person getPersonById(Long id) {
        return entityManager.createNamedQuery("Person.findById", Person.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Optional<Person> findByEmail(String email) {
        Person person = entityManager.createNamedQuery("Person.findByEmail", Person.class)
                .setParameter("email", email)
                .getSingleResult();
        return Optional.of(person);
    }

    public Optional<Person> findByStreetAddress(String streetAddress) {
        Person person = entityManager.createNamedQuery("Person.findByStreetAddress", Person.class)
                .setParameter("streetAddress", streetAddress)
                .getSingleResult();
        return Optional.of(person);
    }

    public Optional<Person> getPersonDetails(Long personId) {
        Person person = entityManager.createNamedQuery("Person.findById", Person.class)
                .setParameter("id", personId)
                .getSingleResult();
        return Optional.of(person);
    }

    public Optional<Person> addPersonAddress(Long personId, PostalAddress newAddress) {
        TypedQuery<Person> queryById = entityManager.createNamedQuery("Person.findById", Person.class).setParameter("id", personId);
        try {
            Person person = queryById.getSingleResult();
            person.getPostalAddresses().add(newAddress);
            entityManager.persist(person);
            return Optional.of(person);
        } catch (NoResultException e) {
            logger.error("Exception at addPersonAddress method for not found entry.");
            return Optional.empty();
        } catch (PersistenceException e) {
            logger.error("Exception at addPersonAddress method for duplicate entry.");
            throw new UserAlreadyExistsException("Duplicate entry for street address.");
        }
    }

    public Optional<Person> addPersonContactMethod(Long personId, ContactMethod contactMethod) {
        TypedQuery<Person> queryById = entityManager.createNamedQuery("Person.findById", Person.class).setParameter("id", personId);
        try {
            Person person = queryById.getSingleResult();
            person.getContactMethods().add(contactMethod);
            entityManager.persist(person);
            return Optional.of(person);
        } catch (NoResultException e) {
            logger.error("Exception at addPersonContactMethod method for not found entry.");
            return Optional.empty();
        } catch (PersistenceException e) {
            logger.error("Exception at addPersonContactMethod method for duplicate entry.");
            throw new UserAlreadyExistsException("Duplicate entry for contact method.");
        }
    }
}
