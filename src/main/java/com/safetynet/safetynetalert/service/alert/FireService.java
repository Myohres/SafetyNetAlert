package com.safetynet.safetynetalert.service.alert;

import com.safetynet.safetynetalert.entity.FireStationEntity;
import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.FireStationService;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsWhenFireDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNamePhoneAgeMedical;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FireService {

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
     * Get FireStations List by address,
     * Send firestation to object firestation list.
     * Get persons List by address,
     * Send persons List to personDTO List,
     * Send personDTO list to object list person
     * @param address String
     * @return Object contain fireStation List and Persons List
     */
    public PersonsWhenFireDTO getPersonsWhenFireByAddress(
            final String address) {
       PersonsWhenFireDTO personWhenFireDTO = new PersonsWhenFireDTO();
        List<Long> fireStations =
                fireStationService.getFireStationByAddress(address)
                        .stream()
                        .map(FireStationEntity::getStation)
                        .collect(Collectors.toList());
        List<PersonNamePhoneAgeMedical> personFireList =
                personService.getPersonsByAddress(address)
                        .stream()
                        .map(any -> {
                            PersonNamePhoneAgeMedical peDTO =
                                    new PersonNamePhoneAgeMedical();
                            peDTO.setPersonPhAgMrDTO(any);
                            return peDTO;
                        }).collect(Collectors.toList());
        personWhenFireDTO.setStations(fireStations);
        personWhenFireDTO.setPersons(personFireList);
        return personWhenFireDTO;
    }
}
