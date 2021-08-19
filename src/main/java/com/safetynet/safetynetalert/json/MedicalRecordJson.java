package com.safetynet.safetynetalert.json;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;

public class MedicalRecordJson {
    /**MedicalRecordJson default Constructor.*/
    public MedicalRecordJson() {
    }
    /** String firstName.*/
    private String firstName;
    /** String lastName.*/
    private String lastName;
    /** String birthdate.*/
    private String birthdate;
    /** String[] medications.*/
    private String[] medications;
    /** String[] allergies.*/
    private String[] allergies;

    /**
     * Getter for firstName.
     * @return String firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for firstName.
     * @param firstNameInput new firstname value to medicalRecordsJson
     */
    public void setFirstName(final String firstNameInput) {
        firstName = firstNameInput;
    }
    /**
     * Getter for lastName.
     * @return String lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for lastName.
     * @param lastNameInput new lastname value to medicalRecordsJson
     */
    public void setLastName(final String lastNameInput) {
        lastName = lastNameInput;
    }
    /**
     * Getter for birthdate.
     * @return String birthdate.
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * Setter for birthdate.
     * @param birthdateInput new birthdate value to medicalRecordsJson
     */
    public void setBirthdate(final String birthdateInput) {
        birthdate = birthdateInput;
    }
    /**
     * Getter for medications.
     * @return String[] medications
     */
    public String[] getMedications() {
        String medicationTemp = JsonStream.serialize(medications);
        return JsonIterator.deserialize(medicationTemp,String[].class);
    }

    /**
     * Setter for medications.
     * @param medicationsInput new medication tab to medicalRecordsJson
     */
    public void setMedications(final String[] medicationsInput) {
        String medicationTemp = JsonStream.serialize(medicationsInput);
        medications = JsonIterator.deserialize(medicationTemp, String[].class);

    }
    /**
     * Getter for allergies.
     * @return String[] allergies
     */
    public String[] getAllergies() {
        String allergiesTemp = JsonStream.serialize(allergies);
        return JsonIterator.deserialize(allergiesTemp,String[].class);
    }

    /**
     * Setter for allergies.
     * @param allergiesInput new allergies tab to medicalRecordsJson
     */
    public void setAllergies(final String[] allergiesInput) {
        String allergiesTemp = JsonStream.serialize(allergiesInput);
        allergies = JsonIterator.deserialize(allergiesTemp, String[].class);
    }
}
