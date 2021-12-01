package com.safetynet.safetynetalert.web.dto.personProfil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonNameAddressPhoneDTO {
    /** Person lastname. */
    private String lastName;
    /** Person firstname. */
    private String firstName;
    /** Person address. */
    private String address;
    /** Person phone. */
    private String phone;
}
