package com.example.demo.repository.dao;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.models.exceptions.UserAlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public PersonDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Person> createPerson(Person person) {
        entityManager.persist(person);
        return Optional.of(person);
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
        TypedQuery<Person> query = entityManager.createNamedQuery("Person.findByIdAddAddress", Person.class)
                .setParameter("id", personId);
        Person person = query.getSingleResult();
        person.getPostalAddresses().add(newAddress);
        entityManager.persist(person);
        return Optional.of(person);
    }

    public Optional<Person> addPersonContactMethod(Long personId, ContactMethod contactMethod) {
        TypedQuery<Person> query = entityManager.createNamedQuery("Person.findByIdAddContact", Person.class)
                .setParameter("id", personId);
        Person person = query.getSingleResult();
        person.getContactMethods().add(contactMethod);
        entityManager.persist(person);
        return Optional.of(person);
    }
}
