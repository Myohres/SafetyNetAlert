package com.safetynet.safetynetalert.web.dto.groupProfil;

import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameAddressPhoneDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonsByFireStationAddressesDTO {

    /**
     * Persons list with Name, address and phone.
     */
    private List<PersonNameAddressPhoneDTO> persons;
    /**
     * Adult number present in foyer.
     */
    private int nbAdult;
    /**
     * Child number present in foyer.
     */
    private int nbChild;

}
