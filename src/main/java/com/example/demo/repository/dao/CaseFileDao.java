package com.example.demo.repository.dao;

import com.example.demo.models.CaseFile;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class CaseFileDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public CaseFileDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(CaseFile caseFile) {
        entityManager.persist(caseFile);
    }

    public List<CaseFile> listAllCaseFiles() {
        return entityManager.createNamedQuery("CaseFile.findAll", CaseFile.class).getResultList();
    }

    public CaseFile getCaseFileById(Long id) {
        return entityManager.createNamedQuery("CaseFile.findById", CaseFile.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Optional<CaseFile> update(CaseFile caseFile) {
        this.entityManager.persist(caseFile);
        return Optional.of(caseFile);
    }
}
