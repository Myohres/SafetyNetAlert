package com.safetynet.safetynetalert.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MedicalRecordEntity {

    /** Medications List.*/
    private List<String> medications;
    /** allergies List. */
    private List<String> allergies;
}
