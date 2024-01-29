package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "p_given_name")
    private String givenName;

    @Column(name = "p_last_name")
    private String lastName;

    @Column(name = "p_date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "p_place_of_birth")
    private String placeOfBirth;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private List<PostalAddress> postalAddresses;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    @Valid
    private List<ContactMethod> contactMethods;
}
