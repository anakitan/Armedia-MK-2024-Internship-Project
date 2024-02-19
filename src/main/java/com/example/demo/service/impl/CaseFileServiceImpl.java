package com.example.demo.service.impl;

import com.example.demo.models.CaseFile;
import com.example.demo.models.Person;
import com.example.demo.models.dto.CaseFileDTO;
import com.example.demo.models.dto.EntityDTOConverter;
import com.example.demo.models.dto.PersonDTO;
import com.example.demo.models.exceptions.PersonNotFoundException;
import com.example.demo.models.exceptions.UserAlreadyExistsException;
import com.example.demo.repository.dao.CaseFileDao;
import com.example.demo.repository.dao.PersonDao;
import com.example.demo.service.CaseFileService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
public class CaseFileServiceImpl implements CaseFileService {

    private final CaseFileDao caseFileDao;
    private final PersonDao personDao;

    public CaseFileServiceImpl(CaseFileDao caseFileDao, PersonDao personDao) {
        this.caseFileDao = caseFileDao;
        this.personDao = personDao;
    }

    @Override
    @Transactional
    public Optional<CaseFileDTO> createCaseFile(CaseFileDTO caseFileDTO) {
        try {
            if (caseFileDTO == null) {
                return Optional.empty();
            }
            Long personId = caseFileDTO.getPersonId();
            Person person = null;
            if (personId != null) {
                Optional<Person> optionalPerson = Optional.ofNullable(personDao.getPersonById(personId));
                if (optionalPerson.isPresent()) {
                    person = optionalPerson.get();
                } else {
                    return Optional.empty();
                }
            } else {
                PersonDTO personDTO = caseFileDTO.getPersonDTO();
                if (personDTO != null) {
                    person = EntityDTOConverter.convertToPersonEntity(personDTO);
                }
            }
            CaseFile caseFile = EntityDTOConverter.convertToCaseFileEntity(caseFileDTO, person);
            if (person != null) {
                caseFile.setPerson(person);
            }
            caseFileDao.create(caseFile);

            caseFileDTO.setId(caseFile.getId());

            return Optional.of(caseFileDTO);
        } catch (EntityExistsException ex) {
            throw new UserAlreadyExistsException(String.format("Case file already exists."));
        }
    }

    @Override
    public List<CaseFile> listAllCaseFiles() {
        return this.caseFileDao.listAllCaseFiles();
    }

    @Override
    public CaseFile getCaseFileById(Long caseFileId) {
        try {
            return this.caseFileDao.getCaseFileById(caseFileId);
        } catch (NoResultException ex) {
            throw new PersonNotFoundException(String.format("Case file with id: %d was not found.", caseFileId));
        }
    }

    @Override
    @Transactional
    public Optional<CaseFile> updateFile(Long id, CaseFileDTO caseFileDTO) {
        try {
            CaseFile caseFile = this.caseFileDao.getCaseFileById(id);

            if (caseFileDTO.getTitle() != null) {
                caseFile.setTitle(caseFileDTO.getTitle());
            }
            if (caseFileDTO.getCaseNumber() != null) {
                caseFile.setCaseNumber(Integer.valueOf(caseFileDTO.getCaseNumber()));
            }
            if (caseFileDTO.getIncidentDate() != null) {
                caseFile.setIncidentDate(caseFileDTO.getIncidentDate());
            }
            return this.caseFileDao.update(caseFile);
        } catch (NoResultException ex) {
            throw new PersonNotFoundException(String.format("Case file with id: %d was not found.", id));
        }
    }
}
