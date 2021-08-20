package entity;

import lombok.Data;

import java.util.Date;

@Data
public class PersonEntity {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private Date birthDate;
    private MedicalRecordEntity medicalRecord;

}

