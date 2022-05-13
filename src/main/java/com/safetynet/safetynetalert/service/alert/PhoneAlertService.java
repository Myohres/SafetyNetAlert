package com.safetynet.safetynetalert.service.alert;

import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.FireStationService;
import com.safetynet.safetynetalert.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PhoneAlertService {

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
     * Get addresses List by FireStation.
     * Get persons List by Adresses List,
     * Get phone List by persons List.
     * @param id long fireStation id
     * @return phone List
     */
    public List<String> getPhoneByFireStation(final long id) {
        List<String> phoneList = new ArrayList<>();
        List<String> addressList =
                fireStationService.getAddressByFireStation(id);
        List<PersonEntity> peList =
                personService.getPersonsByAddresses(addressList);
        peList.forEach(personEntity ->
                phoneList.add(personEntity.getPhone()));
        return phoneList.stream().distinct().collect(Collectors.toList());
    }
}
