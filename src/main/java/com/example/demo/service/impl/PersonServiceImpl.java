package com.example.demo.service.impl;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.models.exceptions.*;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public Optional<Person> createPerson(Person person) {
        return Optional.of(this.personRepository.save(person));
    }

    @Override
    public List<Person> listAllPersons() {
        return this.personRepository.findAll();
    }

    @Override
    public Person getPersonById(Long id) {
        return this.personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        Optional<Person> person = this.personRepository.findByEmail(email);
        if (person.isPresent()) {
            return this.personRepository.findByEmail(email);
        }
        throw new EmailNotFoundException(email);
    }

    @Override
    public Optional<Person> findByStreetAddress(String streetAddress) {
        Optional<Person> person = this.personRepository.findByStreetAddress(streetAddress);
        if (person.isPresent()) {
            return this.personRepository.findByStreetAddress(streetAddress);
        }
        throw new StreetAddressNotFoundException(streetAddress);
    }

    @Override
    public Optional<Person> getPersonDetails(Long personId) {
        return Optional.of(this.personRepository.findById(personId).orElseThrow(() -> new PersonNotFoundException(personId)));
    }

    @Override
    public Optional<Person> addPersonAddress(Long personId, PostalAddress newAddress) {
        Optional<Person> person = this.personRepository.findById(personId);
        if (person.isPresent()) {
            try {
                Person personUpdate = person.get();
                personUpdate.getPostalAddresses().add(newAddress);
                return Optional.of(this.personRepository.save(personUpdate));
            } catch (DataIntegrityViolationException ex) {
                throw new PostalAddressAlreadyExistsException(newAddress);
            }
        }
        throw new PersonNotFoundException(personId);
    }

    @Override
    public Optional<Person> addPersonContactMethod(Long personId, ContactMethod contactMethod) {
        return this.personRepository.findById(personId)
                .map(person -> {
                    person.getContactMethods().add(contactMethod);
                    return this.personRepository.save(person);
                });
    }
}
