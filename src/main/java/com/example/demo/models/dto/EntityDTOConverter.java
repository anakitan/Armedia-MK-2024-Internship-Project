package com.example.demo.models.dto;

import com.example.demo.models.CaseFile;
import com.example.demo.models.Person;

public class EntityDTOConverter {
    public static Person convertToPersonEntity(PersonDTO personDTO) {
        if (personDTO == null) {
            return null;
        }
        Person person = new Person();
        person.setGivenName(personDTO.getGivenName());
        person.setLastName(personDTO.getLastName());
        person.setDateOfBirth(personDTO.getDateOfBirth());
        person.setPlaceOfBirth(personDTO.getPlaceOfBirth());
        person.setPostalAddresses(personDTO.getPostalAddresses());
        person.setContactMethods(personDTO.getContactMethods());
        return person;
    }

    public static CaseFile convertToCaseFileEntity(CaseFileDTO caseFileDTO) {
        if (caseFileDTO == null) {
            return null;
        }
        CaseFile caseFile = new CaseFile();
        caseFile.setCaseNumber(Integer.valueOf(caseFileDTO.getCaseNumber()));
        caseFile.setTitle(caseFileDTO.getTitle());
        caseFile.setIncidentDate(caseFileDTO.getIncidentDate());
        return caseFile;
    }

    public static CaseFileDTO convertToCaseFileDTO(CaseFile caseFile) {
        if (caseFile == null) {
            return null;
        }
        CaseFileDTO caseFileDTO = new CaseFileDTO();
        caseFileDTO.setId(caseFile.getId());
        caseFileDTO.setCaseNumber(String.valueOf(caseFile.getCaseNumber()));
        caseFileDTO.setTitle(caseFile.getTitle());
        caseFileDTO.setIncidentDate(caseFile.getIncidentDate());
        return caseFileDTO;
    }
}
