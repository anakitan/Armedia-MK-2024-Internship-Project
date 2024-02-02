package com.example.demo.service.impl;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.models.exceptions.EmailNotFoundException;
import com.example.demo.models.exceptions.PersonNotFoundException;
import com.example.demo.models.exceptions.PostalAddressAlreadyExistsException;
import com.example.demo.models.exceptions.StreetAddressNotFoundException;
import com.example.demo.service.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceDaoImpl implements PersonService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Person> createPerson(Person person) {
        entityManager.persist(person);
        return Optional.of(person);
    }

    @Override
    public List<Person> listAllPersons() {
        return entityManager.createNamedQuery("Person.findAll", Person.class).getResultList();
    }

    @Override
    public Person getPersonById(Long id) {
        try {
            return entityManager.createNamedQuery("Person.findById", Person.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(id);
        }
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        try {
            Person person = entityManager.createNamedQuery("Person.findByEmail", Person.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(person);
        } catch (RuntimeException ex) {
            throw new EmailNotFoundException(email);
        }
    }

    @Override
    public Optional<Person> findByStreetAddress(String streetAddress) {
        try {
            Person person = entityManager.createNamedQuery("Person.findByStreetAddress", Person.class)
                    .setParameter("streetAddress", streetAddress)
                    .getSingleResult();
            return Optional.of(person);
        } catch (RuntimeException ex) {
            throw new StreetAddressNotFoundException(streetAddress);
        }
    }

    @Override
    public Optional<Person> getPersonDetails(Long personId) {
        try {
            Person person = entityManager.createNamedQuery("Person.findById", Person.class)
                    .setParameter("id", personId)
                    .getSingleResult();
            return Optional.of(person);
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(personId);
        }
    }

    @Override
    @Transactional
    public Optional<Person> addPersonAddress(Long personId, PostalAddress newAddress) {

        TypedQuery<Person> query = entityManager.createNamedQuery("Person.findByIdAddAddress", Person.class)
                .setParameter("id", personId);
        Person person = query.getResultList().stream().findFirst().orElse(null);
        if (person != null) {
            try {
                person.getPostalAddresses().add(newAddress);
                return Optional.of(person);
            } catch (DataIntegrityViolationException ex) {
                throw new PostalAddressAlreadyExistsException(newAddress);
            }
        }
        throw new PersonNotFoundException(personId);
    }

    @Override
    @Transactional
    public Optional<Person> addPersonContactMethod(Long personId, ContactMethod contactMethod) {
        TypedQuery<Person> query = entityManager.createNamedQuery("Person.findByIdAddContact", Person.class)
                .setParameter("id", personId);
        Person person = query.getResultList().stream().findFirst().orElse(null);
        if (person != null) {
            person.getContactMethods().add(contactMethod);
            return Optional.of(person);
        } else {
            throw new PersonNotFoundException(personId);
        }
    }
}
