package com.safetynet.safetynetalert.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@Entity
public class PersonEntity  {
    /** Primary key Id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    /** Medical Records PersonEntity.*/
    @OneToOne(cascade = CascadeType.ALL)
    private MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
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

