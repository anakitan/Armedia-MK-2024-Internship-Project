package com.example.demo.repository.dao;

import com.example.demo.models.User;
import com.example.demo.models.exceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Optional;

@Component
public class UserDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<User> findByUsername(String username) {
        try {
            Query queryByUsername = entityManager.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username);
            User singleResult = (User) queryByUsername.getSingleResult();
            return Optional.of(singleResult);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void register(User user) {
        Query queryByUsername = entityManager.createNamedQuery("User.findByUsername").setParameter("username", user.getUsername());
        if (queryByUsername.getResultList().isEmpty()) {
            try {
                entityManager.persist(user);
            } catch (PersistenceException e) {
                throw new UserAlreadyExistsException("Duplicate entry for email or street address.");
            }
        } else throw new UserAlreadyExistsException("Username already exists.");
        this.entityManager.persist(user);
    }
}
