package com.safetynet.safetynetalert.web.dto.personProfil;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
public class PersonFullDTO {
    /** Person lastname. */
    private String lastName;
    /** Person firstname. */
    private String firstName;
    /** Person address. */
    private String address;
    /** City who live person. */
    private String city;
    /** ZIP city. */
    private String zip;
    /** Person age. */
    private Long age;
    /** Person email. */
    private String email;
    /** Person medical record. */
    private MedicalRecordDTO medicalRecord;

    /**
     * Setter age from birthdate person.
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
