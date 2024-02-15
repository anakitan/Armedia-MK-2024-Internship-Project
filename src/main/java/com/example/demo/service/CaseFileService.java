package com.example.demo.service;

import com.example.demo.models.CaseFile;

import java.util.List;
import java.util.Optional;

public interface CaseFileService {
    Optional<CaseFile> createCaseFile(CaseFile caseFile);

    List<CaseFile> listAllCaseFiles();

    CaseFile getCaseFileById(Long caseFileId);

    Optional<CaseFile> updateFile(Long id, CaseFile caseFile);
}
