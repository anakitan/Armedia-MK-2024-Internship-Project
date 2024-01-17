package com.example.demo.service;

import com.example.demo.models.Person;
import com.example.demo.service.impl.PersonServiceImpl;

import java.util.List;

public interface PersonService {

    public Person createPerson(Person person);
    public List<Person> listAllPersons();
    public Person getPersonById(Long id);
}
