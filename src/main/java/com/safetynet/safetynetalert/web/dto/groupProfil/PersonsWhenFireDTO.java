package com.safetynet.safetynetalert.web.dto.groupProfil;

import com.safetynet.safetynetalert.web.dto.personProfil.PersonNamePhoneAgeMedical;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonsWhenFireDTO {

    /**
     * Stations list.
     */
    private List<Long> stations;

    /**
     * Persons list contain Name, phone, age and medical record.
     */
    private List<PersonNamePhoneAgeMedical> persons;

}
