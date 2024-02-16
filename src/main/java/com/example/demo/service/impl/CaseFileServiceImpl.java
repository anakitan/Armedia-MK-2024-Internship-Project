package com.example.demo.service.impl;

import com.example.demo.models.CaseFile;
import com.example.demo.models.dto.CaseFileDTO;
import com.example.demo.models.exceptions.PersonNotFoundException;
import com.example.demo.repository.dao.CaseFileDao;
import com.example.demo.service.CaseFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CaseFileServiceImpl implements CaseFileService {

    private final CaseFileDao caseFileDao;

    public CaseFileServiceImpl(CaseFileDao caseFileDao) {
        this.caseFileDao = caseFileDao;
    }

    @Override
    @Transactional
    public Optional<CaseFileDTO> createCaseFile(CaseFileDTO caseFileDTO) {
        return this.caseFileDao.create(caseFileDTO);
    }

    @Override
    public List<CaseFile> listAllCaseFiles() {
        return this.caseFileDao.listAllCaseFiles();
    }

    @Override
    public CaseFile getCaseFileById(Long caseFileId) {
        try {
            return this.caseFileDao.getCaseFileById(caseFileId);
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(String.format("Case file with id: %d was not found.", caseFileId));
        }
    }

    @Override
    @Transactional
    public Optional<CaseFile> updateFile(Long id, CaseFileDTO caseFileDTO) {
        try {
            return this.caseFileDao.update(id, caseFileDTO);
        } catch (RuntimeException ex) {
            throw new PersonNotFoundException(String.format("Case file with id: %d was not found.", id));
        }
    }
}
