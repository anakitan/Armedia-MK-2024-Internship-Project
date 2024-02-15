package com.example.demo.models;

import com.example.demo.models.enumerations.ContactType;
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
@ValidContactMethodValue
@Table(name = "p_contact_method")
public class ContactMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_contact_method_id")
    private Long id;

    @Column(name = "p_type")
    private ContactType type;

    @Column(name = "p_value", unique = true)
    private String value;

    @Column(name = "p_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonBackReference
    private Person person;
}

