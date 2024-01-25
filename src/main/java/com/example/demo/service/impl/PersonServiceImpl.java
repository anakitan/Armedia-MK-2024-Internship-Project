package com.example.demo.service.impl;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import com.example.demo.models.PostalAddress;
import com.example.demo.repository.ContactMethodRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.PostalAddressRepository;
import com.example.demo.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.demo.models.ContactMethod.isValidEmail;
import static com.example.demo.models.ContactMethod.isValidPhoneNumber;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository,
                             PostalAddressRepository postalAddressRepository,
                             ContactMethodRepository contactMethodRepository) {
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
    public Optional<Person> editPersonAddress(Long personId, PostalAddress newAddress) {
        return this.personRepository.findById(personId)
                .map(person -> {
                    person.getPostalAddresses().add(newAddress);
                    return this.personRepository.save(person);
                });
    }

    @Override
    public Optional<Person> editPersonContactMethod(Long personId, ContactMethod contactMethod) {
        if (contactMethod.getType() == ContactMethod.ContactType.EMAIL) {
            if (!isValidEmail(contactMethod.getValue())) {
                throw new IllegalArgumentException("Invalid email format");
            }
        } else if (contactMethod.getType() == ContactMethod.ContactType.PHONE) {
            if (!isValidPhoneNumber(contactMethod.getValue())) {
                throw new IllegalArgumentException("Invalid phone number format");
            }
        } else {
            throw new IllegalArgumentException("Unsupported contact method type");
        }
        return this.personRepository.findById(personId)
                .map(person -> {
                    person.getContactMethods().add(contactMethod);
                    return this.personRepository.save(person);
                });
    }
}
