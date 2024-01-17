package com.example.demo.models;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String given_name;

    private String family_name;

    @Temporal(TemporalType.DATE)
    private Date birth_of_date;

    private String place_of_birth;

    public Person() {}
    
    public Person(Long id, String given_name, String family_name, Date birth_of_date, String place_of_birth) {
        this.id = id;
        this.given_name = given_name;
        this.family_name = family_name;
        this.birth_of_date = birth_of_date;
        this.place_of_birth = place_of_birth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public Date getBirth_of_date() {
        return birth_of_date;
    }

    public void setBirth_of_date(Date birth_of_date) {
        this.birth_of_date = birth_of_date;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }
}
