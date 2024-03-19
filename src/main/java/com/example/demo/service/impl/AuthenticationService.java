package com.example.demo.service.impl;

import com.example.demo.auth.AuthenticationRequest;
import com.example.demo.auth.AuthenticationResponse;
import com.example.demo.auth.RegisterRequest;
import com.example.demo.config.JwtService;
import com.example.demo.models.User;
import com.example.demo.models.exceptions.InvalidUsernameOrPasswordException;
import com.example.demo.models.exceptions.PersonNotFoundException;
import com.example.demo.models.exceptions.UserAlreadyExistsException;
import com.example.demo.repository.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterRequest request) {

        if (userDao.findByUsername(request.getUsername()).isPresent()) {
            logger.error("Exception in register method from AuthenticationService for duplicate user");
            throw new UserAlreadyExistsException(String.format("User with username: %s already exists.", request.getUsername()));
        }
        logger.info("register method from AuthenticationService started");
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .person(request.getPerson())
                .build();
        userDao.register(user);
        return user;
//       String jwtToken = jwtService.generateToken(user);
//       return AuthenticationResponse.builder()
//               .token(jwtToken)
//               .build();
//       return String.format("User with username %s has been registered successfully.", user.getUsername());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            logger.info("authenticate method from AuthenticationService started");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (RuntimeException ex) {
            logger.error("Exception in authenticate method from AuthenticationService for invalid username or password");
            throw new InvalidUsernameOrPasswordException();
        }
        logger.info("Successfully authenticated user.");
        User user = userDao.findByUsername(request.getUsername())
                .orElseThrow(() -> new PersonNotFoundException(String.format("User: %s does not exist.", request.getUsername())));
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
