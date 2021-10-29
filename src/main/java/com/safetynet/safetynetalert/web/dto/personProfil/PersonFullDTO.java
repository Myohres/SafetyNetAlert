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
    private String lastName;
    private String firstName;
    private String address;
    private String city;
    private String zip;
    private Long age;
    private String email;
    private MedicalRecordDTO medicalRecord;

    public void setAge(final Date birthDate) {
        LocalDate localBirthDate = birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());
        this.age = ChronoUnit.YEARS.between(localBirthDate, currentDate);
    }
}
