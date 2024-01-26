package com.example.demo.service.impl;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
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
        return this.personRepository.findById(id).orElse(null);
    }

    @Override
    public List<Person> findByEmail(String email) {
        return this.personRepository.findByEmail(email);
    }

    @Override
    public List<Person> findByStreetAddress(String streetAddress) {
        return this.personRepository.findByStreetAddress(streetAddress);
    }

    @Override
    public Optional<Person> getPersonDetails(Long personId) {
        return this.personRepository.findById(personId);
    }

    @Override
    public Optional<Person> addPersonAddress(Long personId, PostalAddress newAddress) {
        return this.personRepository.findById(personId)
                .map(person -> {
                    person.getPostalAddresses().add(newAddress);
                    return this.personRepository.save(person);
                });
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
