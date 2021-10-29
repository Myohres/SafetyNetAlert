package com.safetynet.safetynetalert.web.dto.personProfil;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MedicalRecordDTO {
    private List<String> medications;
    private List<String> allergies;

    public void setMedicalRecordDTO(final MedicalRecordEntity mre) {
        this.medications = mre.getMedications();
        this.allergies = mre.getAllergies();
    }
}
