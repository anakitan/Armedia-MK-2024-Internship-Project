package com.example.demo.auth;

import com.example.demo.models.Person;
import com.example.demo.models.enumerations.Role;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String username;
    private String password;
    private List<Role> roles;
    private Person person;
}
