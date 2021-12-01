package com.safetynet.safetynetalert.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class MedicalRecordEntity {
    /**id MedicalRecord.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Medications List.*/
    @ElementCollection
    private List<String> medications = new ArrayList<>();

    /** allergies List. */
    @ElementCollection
     private List<String> allergies = new ArrayList<>();
}
