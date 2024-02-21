package com.example.demo.service.impl;

import com.example.demo.models.CaseFile;
import com.example.demo.models.Person;
import com.example.demo.models.dto.CaseFileDTO;
import com.example.demo.models.dto.EntityDTOConverter;
import com.example.demo.models.dto.PersonDTO;
import com.example.demo.models.exceptions.InvalidUsernameOrPasswordException;
import com.example.demo.models.exceptions.PersonNotFoundException;
import com.example.demo.models.exceptions.UserAlreadyExistsException;
import com.example.demo.repository.dao.CaseFileDao;
import com.example.demo.repository.dao.PersonDao;
import com.example.demo.service.CaseFileService;
import com.example.demo.web.CaseFileController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Service
public class CaseFileServiceImpl implements CaseFileService {

    Logger logger = LoggerFactory.getLogger(CaseFileController.class);

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
            logger.info("createCaseFile method in service class started");
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
        } catch (PersistenceException ex) {
            logger.info("Exception at createCaseFile method in service class for duplicate entry.");
            throw new UserAlreadyExistsException(String.format("Case file already exists."));
        }
    }

    @Override
    public List<CaseFile> listAllCaseFiles() {
        try {
            logger.info("listAllCaseFiles method in service class started");
            return this.caseFileDao.listAllCaseFiles();
        } catch (NoResultException ex) {
            logger.error("Exception in listAllCaseFiles of service class");
            throw new PersonNotFoundException(String.format("The list of case files was not found."));
        }
    }

    @Override
    public CaseFile getCaseFileById(Long caseFileId) {
        try {
            logger.info("getCaseFileById method in service class started");
            return this.caseFileDao.getCaseFileById(caseFileId);
        } catch (NoResultException ex) {
            logger.error("Exception in getCaseFileById of service class");
            throw new PersonNotFoundException(String.format("Case file with id: %d was not found.", caseFileId));
        }
    }

    @Override
    @Transactional
    public Optional<CaseFile> updateFile(Long id, CaseFileDTO caseFileDTO) {
        try {
            logger.info("update method from CaseFile in service class started");
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
            logger.error("Exception in update method from CaseFile of service class");
            throw new PersonNotFoundException(String.format("Case file with id: %d was not found.", id));
        }
    }
}
