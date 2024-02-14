package com.example.demo.repository.dao;

import com.example.demo.models.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        entityManager.persist(user);
    }
}
