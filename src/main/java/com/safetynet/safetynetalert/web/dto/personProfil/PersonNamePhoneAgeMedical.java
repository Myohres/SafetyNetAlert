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
    /** Person lastname. */
    private String lastName;
    /** Person firstname. */
    private String firstName;
    /** Person phone. */
    private String phone;
    /** Person age. */
    private Long age;
    /** Person medical record. */
    private MedicalRecordDTO medicalRecord;

    /**
     * Setter from a personEntity.
     * @param pe personEntity information
     */
    public void setPersonPhAgMrDTO(final PersonEntity pe) {
        setLastName(pe.getLastName());
        setFirstName(pe.getFirstName());
        setPhone(pe.getPhone());
        setAge(pe.getBirthDate());
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setMedicalRecordDTO(pe.getMedicalRecord());
        setMedicalRecord(medicalRecordDTO);
    }

    /**
     * Setter age from birthdate.
     * @param birthDate birthdate person
     */
    public void setAge(final Date birthDate) {
        LocalDate localBirthDate = birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());
        this.age = ChronoUnit.YEARS.between(localBirthDate, currentDate);
    }
}
