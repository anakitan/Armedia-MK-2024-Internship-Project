package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostalAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postal_address_id")
    private Long id;

    @Column(name = "street_address", unique = true)
    private String streetAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "zip")
    private String zip;

    @Column(name = "country")
    private String country;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonBackReference
    private Person person;
}
