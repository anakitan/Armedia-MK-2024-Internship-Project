package com.example.demo.service.impl;

import com.example.demo.models.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person createPerson(Person person) {
        return this.personRepository.save(person);
    }

    @Override
    public List<Person> listAllPersons() {
        return this.personRepository.findAll();
    }

    @Override
    public Person getPersonById(Long id) {
        return this.personRepository.findById(id).orElse(null);
    }
}
