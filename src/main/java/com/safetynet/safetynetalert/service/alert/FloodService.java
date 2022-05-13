package com.safetynet.safetynetalert.service.alert;

import com.safetynet.safetynetalert.service.FireStationService;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNamePhoneAgeMedical;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FloodService {

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
     * Get addresses list by fireStation id.
     * for each address from addresses list
     *  Get personsEntity List by address
     *  send personEntity to personDTO
     *  add personDTO to List personsDTO
     *  add list personsDTO and address to foyerDTO
     * @param fireStations List id fireStations
     * @return object list : Address List with persons associated
     */
    public Map<String, List<PersonNamePhoneAgeMedical>> getFoyersByFireStations(
            final List<Long> fireStations) {
        Map<String, List<PersonNamePhoneAgeMedical>> foyerMap = new TreeMap<>();
        List<String> addressList =
                fireStationService.getAddressByFireStations(fireStations);
        addressList.forEach(address -> {
            List<PersonNamePhoneAgeMedical> personsDTOList =
                    personService.getPersonsByAddress(address)
                            .stream()
                            .map(any -> {
                                PersonNamePhoneAgeMedical peDTO =
                                        new PersonNamePhoneAgeMedical();
                                peDTO.setPersonPhAgMrDTO(any);
                                return peDTO;
                            }).collect(Collectors.toList());
            foyerMap.put(address, personsDTOList);
        });
        return foyerMap;
    }
}
