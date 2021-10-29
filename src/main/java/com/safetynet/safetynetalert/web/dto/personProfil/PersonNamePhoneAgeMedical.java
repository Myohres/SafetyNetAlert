package com.safetynet.safetynetalert.web.dto.personProfil;

import com.safetynet.safetynetalert.entity.PersonEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
public class PersonNamePhoneAgeMedical {
    private String lastName;
    private String firstName;
    private String phone;
    private Long age;
    private MedicalRecordDTO medicalRecord;

    public void setPersonPhAgMrDTO(final PersonEntity pe) {
        setLastName(pe.getLastName());
        setFirstName(pe.getFirstName());
        setPhone(pe.getPhone());
        setAge(pe.getBirthDate());
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setMedicalRecordDTO(pe.getMedicalRecord());
        setMedicalRecord(medicalRecordDTO);
    }

    public void setAge(final Date birthDate) {
        LocalDate localBirthDate = birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());
        this.age = ChronoUnit.YEARS.between(localBirthDate, currentDate);
    }
}
