package com.safetynet.safetynetalert.web.dto.groupProfil;

import com.safetynet.safetynetalert.web.dto.personProfil.PersonNamePhoneAgeMedical;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonsWhenFireDTO {

    private List<Long> stations;
    private List<PersonNamePhoneAgeMedical> persons;

}
