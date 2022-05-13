package com.safetynet.safetynetalert.service.alert;

import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.FireStationService;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsByFireStationAddressesDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameAddressPhoneDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PersonsByStationService {

    /**
     * Person Service layer.
     */
    @Autowired
    private PersonService personService;

    /**
     * FireStation service layer.
     */
    @Autowired
    private FireStationService fireStationService;

    /**
     * Get addresses list By firestation.
     * Get persons by this address List collected,
     * iterate adult and child number.
     * @param id long FireStation id
     * @return List Persons
     */
    public PersonsByFireStationAddressesDTO getPersonByFireStation(
            final long id) {
        PersonsByFireStationAddressesDTO personFFSA =
                new PersonsByFireStationAddressesDTO();
        List<PersonNameAddressPhoneDTO> personsList = new ArrayList<>();
        List<String> addressList =
                fireStationService.getAddressByFireStation(id);
        List<PersonEntity> peList =
                personService.getPersonsByAddresses(addressList);
        peList.forEach(personEntity -> {
            PersonNameAddressPhoneDTO person = new PersonNameAddressPhoneDTO();
            person.setLastName(personEntity.getLastName());
            person.setFirstName(personEntity.getFirstName());
            person.setAddress(personEntity.getAddress());
            person.setPhone(personEntity.getPhone());
            if (personService.majorPerson(personEntity)) {
                personFFSA.setNbAdult(personFFSA.getNbAdult() + 1);
            } else {
                personFFSA.setNbChild(personFFSA.getNbChild() + 1);
            }
            personsList.add(person);
        });
        personFFSA.setPersons(personsList);
        return personFFSA;
    }
}
