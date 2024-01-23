package com.example.demo.service.impl;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.repository.ContactMethodRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.PostalAddressRepository;
import com.example.demo.service.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final PostalAddressRepository postalAddressRepository;

    private final ContactMethodRepository contactMethodRepository;

    public PersonServiceImpl(PersonRepository personRepository,
                             PostalAddressRepository postalAddressRepository,
                             ContactMethodRepository contactMethodRepository) {
        this.personRepository = personRepository;
        this.postalAddressRepository = postalAddressRepository;
        this.contactMethodRepository = contactMethodRepository;
    }

    @Override
    @Transactional
    public Person createPerson(Person person) {

        for (PostalAddress postalAddress : person.getPostalAddresses()) {
            postalAddress.setPerson(person);
        }
        for (ContactMethod contactMethod : person.getContactMethods()) {
            contactMethod.setPerson(person);
        }
        return personRepository.save(person);
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
}
