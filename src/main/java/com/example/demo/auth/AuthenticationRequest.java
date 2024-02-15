package com.example.demo.auth;

import lombok.*;

@Data
@Builder
public class AuthenticationRequest {

    private final String username;

    private final String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
