package com.safetynet.safetynetalert.web.dto.groupProfil;

import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameAgeDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FamilyByAddressDTO {

    /**
     * Child list with Name and age.
     */
    private List<PersonNameAgeDTO> childList;
    /**
     * Adult list with just name.
     */
    private List<PersonNameDTO> adultList;
}
