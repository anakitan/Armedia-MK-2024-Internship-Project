package com.example.demo.auth;

import com.example.demo.models.Person;
import com.example.demo.models.enumerations.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String username;
    private String password;
    private List<Role> roles;
    private Person person;
}
