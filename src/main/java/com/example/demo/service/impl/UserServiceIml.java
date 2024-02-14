package com.example.demo.service.impl;

import com.example.demo.models.exceptions.PersonNotFoundException;
import com.example.demo.repository.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIml implements UserDetailsService {

    private final UserDao userDao;

    public UserServiceIml(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws PersonNotFoundException {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new PersonNotFoundException(String.format("User %s does not exist", username)));
    }
}
