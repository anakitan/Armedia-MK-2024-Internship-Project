package com.example.demo.web;

import com.example.demo.models.CaseFile;
import com.example.demo.service.impl.CaseFileServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/caseFiles")
public class CaseFileController {

    private final CaseFileServiceImpl caseFileService;

    public CaseFileController(CaseFileServiceImpl caseFileService) {
        this.caseFileService = caseFileService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Optional<CaseFile>> createCaseFile(@RequestBody CaseFile caseFile) {
        return new ResponseEntity<>(this.caseFileService.createCaseFile(caseFile), HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public List<CaseFile> listAllCaseFiles() {
        return this.caseFileService.listAllCaseFiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaseFile> getCaseFileById(@PathVariable Long id) {
        return new ResponseEntity<>(this.caseFileService.getCaseFileById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}/update")
    public ResponseEntity<Optional<CaseFile>> update(@PathVariable Long id, @RequestBody CaseFile caseFile) {
        return new ResponseEntity<>(this.caseFileService.updateFile(id, caseFile), HttpStatus.OK);
    }
}
