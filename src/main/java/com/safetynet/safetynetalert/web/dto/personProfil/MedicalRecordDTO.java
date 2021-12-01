package com.safetynet.safetynetalert.web.dto.personProfil;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MedicalRecordDTO {
    /**
     * Medications list.
     */
    private List<String> medications;
    /**
     * Allergies list.
     */
    private List<String> allergies;

    /**
     * Setter from a medical record to get their medications and allergies list.
     * @param mre medical record to take medications and allergies
     */
    public void setMedicalRecordDTO(final MedicalRecordEntity mre) {
        this.medications = mre.getMedications();
        this.allergies = mre.getAllergies();
    }
}
