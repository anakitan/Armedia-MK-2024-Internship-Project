package com.example.demo.repository.dao;

import com.example.demo.models.User;
import com.example.demo.models.exceptions.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class UserDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User findByUsername(String username) {
        try {
            Query queryByUsername = entityManager.createNamedQuery("User.findByUsername").setParameter("username", username);
            return (User) queryByUsername.getSingleResult();
        } catch (RuntimeException exception) {
            throw new com.example.demo.models.exceptions.UsernameNotFoundException();
        }
    }

    @Transactional
    public void register(User user) {
        entityManager.persist(user);
        entityManager.persist(user.getPerson());
    }
}
