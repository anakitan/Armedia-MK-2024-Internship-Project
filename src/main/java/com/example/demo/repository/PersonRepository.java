package com.example.demo.repository;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p JOIN p.contactMethods c WHERE c.type='EMAIL' AND c.value = :email")
    List<Person> findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Person p JOIN p.postalAddresses pa WHERE pa.streetAddress = :streetAddress")
    List<Person> findByStreetAddress(@Param("streetAddress") String streetAddress);
}
