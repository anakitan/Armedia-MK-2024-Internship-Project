package com.example.demo.models;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String givenName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String placeOfBirth;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private List<PostalAddress> postalAddresses;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private List<ContactMethod> contactMethods;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public List<PostalAddress> getPostalAddresses() {
        return postalAddresses;
    }

    public void setPostalAddresses(List<PostalAddress> postalAddresses) {
        this.postalAddresses = postalAddresses;
    }

    public List<ContactMethod> getContactMethods() {
        return contactMethods;
    }

    public void setContactMethods(List<ContactMethod> contactMethods) {
        this.contactMethods = contactMethods;
    }
}
