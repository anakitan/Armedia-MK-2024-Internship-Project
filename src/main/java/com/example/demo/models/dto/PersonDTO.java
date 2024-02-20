package com.example.demo.models.dto;

import com.example.demo.models.ContactMethod;
import com.example.demo.models.PostalAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    private Long id;

    private String givenName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String placeOfBirth;

    private List<PostalAddress> postalAddresses;

    private List<ContactMethod> contactMethods;
}
