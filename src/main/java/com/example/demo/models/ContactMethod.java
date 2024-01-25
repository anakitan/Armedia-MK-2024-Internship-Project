package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactMethod {

    public enum ContactType {
        EMAIL, PHONE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_method_id")
    private Long id;

    @Column(name = "type")
    private ContactType type;

    @Column(name = "value", unique = true)
    private String value;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonBackReference
    private Person person;

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[0-9]{1,3}(?:/[0-9]{1,3}){0,2}$";       //^[0-9]{10}$
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}

