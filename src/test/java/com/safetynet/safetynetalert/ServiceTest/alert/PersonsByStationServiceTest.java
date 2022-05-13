package com.safetynet.safetynetalert.ServiceTest.alert;

import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.FireStationService;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.service.alert.PersonsByStationService;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsByFireStationAddressesDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonsByStationServiceTest {

    @InjectMocks
    private PersonsByStationService personsByStationService;

    @Mock
    private PersonService personService;

    @Mock
    private FireStationService fireStationService;

    @Test
    void getPersonByFireStation() {
        List<String> addressList = new ArrayList<>();
        addressList.add("NoWhere");
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity pe1 = new PersonEntity();
        pe1.setLastName("Bond");
        pe1.setFirstName("James");
        pe1.setAddress("NoWhere");
        pe1.setPhone("007");
        Date birthDate = new Date(System.currentTimeMillis()-(31536000000L*25));
        pe1.setBirthDate(birthDate);
        peList.add(pe1);
        PersonEntity pe2 = new PersonEntity();
        pe2.setLastName("Holmes");
        pe2.setFirstName("Sherlock");
        pe2.setAddress("NoWhere");
        pe2.setPhone("123456");
        Date birthDate2 = new Date(System.currentTimeMillis()-(31536000000L*15));
        pe2.setBirthDate(birthDate2);
        peList.add(pe2);
        when(fireStationService.getAddressByFireStation(1L)).thenReturn(addressList);
        when(personService.getPersonsByAddresses(addressList)).thenReturn(peList);
        when(personService.majorPerson(pe1)).thenReturn(true);
        when(personService.majorPerson(pe2)).thenReturn(false);

        PersonsByFireStationAddressesDTO persons = personsByStationService.getPersonByFireStation(1);

        assertEquals(1, persons.getNbAdult());
        assertEquals(1, persons.getNbChild());
        assertEquals(2, persons.getPersons().size());
    }
}