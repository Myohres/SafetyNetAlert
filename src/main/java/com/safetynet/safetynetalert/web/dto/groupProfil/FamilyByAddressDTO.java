package com.safetynet.safetynetalert.web.dto.groupProfil;

import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameAgeDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FamilyByAddressDTO {

    private List<PersonNameAgeDTO> childList;
    private List<PersonNameDTO> adultList;
}
