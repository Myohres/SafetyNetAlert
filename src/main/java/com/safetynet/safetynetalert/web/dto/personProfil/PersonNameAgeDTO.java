package com.safetynet.safetynetalert.web.dto.personProfil;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
public class PersonNameAgeDTO {
    /** Person lastname. */
    private String lastName;
    /** Person firstname. */
    private String firstName;
    /** Person age. */
    private long age;

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
