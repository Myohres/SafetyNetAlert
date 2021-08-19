package com.safetynet.safetynetalert.json;

public class PersonJson {
    /** Default constructor PersonJson.*/
    public PersonJson() {
    }
    /** String firstName.*/
    private String firstName;
    /** String lastName.*/
    private String lastName;
    /** String address.*/
    private String address;
    /** String city.*/
    private String city;
    /** String zip.*/
    private String zip;
    /** String phone.*/
    private String phone;
    /** String email.*/
    private String email;

    /**
     * Getter for firstName.
     * @return String firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for firstName.
     * @param firstNameInput new firstname value to PersonJson
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
     * @param lastNameInput new lastname value to PersonJson
     */
    public void setLastName(final String lastNameInput) {
        lastName = lastNameInput;
    }

    /**
     * Getter for address.
     * @return String address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for address.
     * @param addressInput new address value to PersonJson
     */
    public void setAddress(final String addressInput) {
        address = addressInput;
    }

    /**
     * Getter for city.
     * @return String city
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter for city.
     * @param cityInput new city value to PersonJson
     */
    public void setCity(final String cityInput) {
        city = cityInput;
    }

    /**
     * Getter for zip.
     * @return String zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Setter for zip.
     * @param zipInput new zip value to PersonJson
     */
    public void setZip(final String zipInput) {
        zip = zipInput;
    }

    /**
     * Getter for phone.
     * @return String phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for phone.
     * @param phoneInput new phone number to PersonJson
     */
    public void setPhone(final String phoneInput) {
        phone = phoneInput;
    }

    /**
     * Getter for email.
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email.
     * @param emailInput new email value to PersonJson
     */
    public void setEmail(final String emailInput) {
        email = emailInput;
    }
}
