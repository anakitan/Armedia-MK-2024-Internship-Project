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
@Table(name = "p_postal_address")
@NamedQuery(name = "PostalAddress.findById", query = "SELECT pa FROM PostalAddress pa WHERE pa.id = :id")
public class PostalAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_postal_address_id")
    private Long id;

    @Column(name = "p_street_address", unique = true)
    private String streetAddress;

    @Column(name = "p_city")
    private String city;

    @Column(name = "p_zip")
    private String zip;

    @Column(name = "p_country")
    private String country;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonBackReference
    private Person person;
}
