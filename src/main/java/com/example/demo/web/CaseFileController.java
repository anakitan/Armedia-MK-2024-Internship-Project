package com.example.demo.web;

import com.example.demo.models.CaseFile;
import com.example.demo.models.dto.CaseFileDTO;
import com.example.demo.service.impl.CaseFileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/caseFiles")
public class CaseFileController {

    Logger logger = LoggerFactory.getLogger(CaseFileController.class);

    private final CaseFileServiceImpl caseFileService;

    public CaseFileController(CaseFileServiceImpl caseFileService) {
        this.caseFileService = caseFileService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Optional<CaseFileDTO>> createCaseFile(@RequestBody CaseFileDTO caseFileDTO) {
        logger.info("Starting createCaseFile method with info log level");
        return new ResponseEntity<>(this.caseFileService.createCaseFile(caseFileDTO), HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public List<CaseFile> listAllCaseFiles() {
        logger.info("Starting listAllCaseFiles method with info log level");
        return this.caseFileService.listAllCaseFiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaseFile> getCaseFileById(@PathVariable Long id) {
        logger.info("Starting getCaseFileById method with info log level");
        return new ResponseEntity<>(this.caseFileService.getCaseFileById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<CaseFile>> update(@PathVariable Long id, @RequestBody CaseFileDTO caseFileDTO) {
        logger.info("Starting update method for CaseFile with info log level");
        return new ResponseEntity<>(this.caseFileService.updateFile(id, caseFileDTO), HttpStatus.OK);
    }
}
