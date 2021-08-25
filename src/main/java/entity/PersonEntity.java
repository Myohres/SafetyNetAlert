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

    public void setBirthDate(final Date birthDateInput) {
        if (birthDateInput == null) {
            this.birthDate = null;
        } else {
            this.birthDate = new Date(birthDateInput.getTime());
        }
    }

    public Date getBirthDate() {
        if (this.birthDate == null) {
            return null;
        }
        return new Date(this.birthDate.getTime());
    }
}

