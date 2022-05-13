package com.safetynet.safetynetalert.service.alert;

import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.web.dto.groupProfil.FamilyByAddressDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameAgeDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChildAlertService {

    /**
     * Person Service layer.
     */
    @Autowired
    private PersonService personService;

    /**
     * Get child list by address.
     * Get adult List by address.
     * @param address String
     * @return Family object contain the two lists
     */
    public FamilyByAddressDTO getFamilyByAddress(final String address) {
        FamilyByAddressDTO familyByAddressDTO = new FamilyByAddressDTO();
        List<PersonEntity> family = personService.getPersonsByAddress(address);
        List<PersonNameAgeDTO> childList = family
                .stream()
                .filter(personEntity ->
                        (!(personService.majorPerson(personEntity))))
                .map(personEntity -> {
                    PersonNameAgeDTO child = new PersonNameAgeDTO();
                    child.setLastName(personEntity.getLastName());
                    child.setFirstName(personEntity.getFirstName());
                    child.setAge(personEntity.getBirthDate());
                    return child;
                }).collect(Collectors.toList());
        List<PersonNameDTO> adultList = family
                .stream()
                .filter(personEntity -> personService.majorPerson(personEntity))
                .map(personEntity -> {
                    PersonNameDTO adult = new PersonNameDTO();
                    adult.setLastName(personEntity.getLastName());
                    adult.setFirstName(personEntity.getFirstName());
                    return adult;
                }).collect(Collectors.toList());
        familyByAddressDTO.setAdultList(adultList);
        familyByAddressDTO.setChildList(childList);
        return familyByAddressDTO;
    }
}
