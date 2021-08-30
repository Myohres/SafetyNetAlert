package com.safetynet.safetynetalert.entity;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import lombok.Data;

import java.util.Date;

@Data
public class PersonEntity {
    /** Firstname PersonEntity. */
    private String firstName;
    /** Lastname PersonEntity. */
    private String lastName;
    /** Address PersonEntity. */
    private String address;
    /** City PersonEntity. */
    private String city;
    /** ZIP PersonEntity. */
    private String zip;
    /** Phone PersonEntity. */
    private String phone;
    /** Email PersonEntity. */
    private String email;
    /** Birthdate PersonEntity. */
    private Date birthDate;
    /** Medical Records PersonEntity. */
    private MedicalRecordEntity medicalRecord;

    /**
     * Setter for BirthDate.
     * @param birthDateInput new Date Birthdate
     */
    public void setBirthDate(final Date birthDateInput) {
        if (birthDateInput == null) {
            this.birthDate = null;
        } else {
            this.birthDate = new Date(birthDateInput.getTime());
        }
    }

    /**
     * Getter for BirthDate.
     * @return Date Birthdate
     */
    public Date getBirthDate() {
        if (this.birthDate == null) {
            return null;
        }
        return new Date(this.birthDate.getTime());
    }
}

