package com.safetynet.safetynetalert.web.dto.groupProfil;

import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameAddressPhoneDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonsByFireStationAddressesDTO {

    private List<PersonNameAddressPhoneDTO> persons;
    private int nbAdult;
    private int nbChild;

}
