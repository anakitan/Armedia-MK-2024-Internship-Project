package com.example.demo.repository.dao;

import com.example.demo.models.CaseFile;
import com.example.demo.models.exceptions.PersonNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class CaseFileDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public CaseFileDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<CaseFile> create(CaseFile caseFile) {
        entityManager.persist(caseFile);
        return Optional.of(caseFile);
    }

    public List<CaseFile> listAllCaseFiles() {
        return entityManager.createNamedQuery("CaseFile.findAll", CaseFile.class).getResultList();
    }

    public CaseFile getCaseFileById(Long id) {
        return entityManager.createNamedQuery("CaseFile.findById", CaseFile.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Optional<CaseFile> update(Long id, CaseFile updatedFile) {
        Query query = entityManager.createNamedQuery("CaseFile.updateFile")
                .setParameter("caseNumber", updatedFile.getCaseNumber())
                .setParameter("title", updatedFile.getTitle())
                .setParameter("incidentDate", updatedFile.getIncidentDate())
                .setParameter("id", id);
        query.executeUpdate();
        CaseFile updatedCaseFile = entityManager.find(CaseFile.class, id);
        if (updatedCaseFile != null) {
            return Optional.of(updatedCaseFile);
        } else {
            throw new PersonNotFoundException(String.format("Case file with id: %d was not found.", id));
        }
    }
}
