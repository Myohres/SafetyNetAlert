package com.safetynet.safetynetalert.entity;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecordEntity {

    /** Medications List.*/
    private List<String> medications;
    /** allergies List. */
    private List<String> allergies;
}
