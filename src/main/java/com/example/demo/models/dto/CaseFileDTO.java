package com.example.demo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaseFileDTO {

    private String caseNumber;
    private String title;
    private Date incidentDate;
    @Valid
    private PersonDTO personDTO;
    private Long personId;
    private Long id;
}
