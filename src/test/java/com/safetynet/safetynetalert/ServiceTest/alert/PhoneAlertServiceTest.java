package com.safetynet.safetynetalert.ServiceTest.alert;

import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.FireStationService;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.service.alert.PhoneAlertService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneAlertServiceTest {

    @InjectMocks
    private PhoneAlertService phoneAlertService;

    @Mock
    private PersonService personService;

    @Mock
    private FireStationService fireStationService;

    @Test
    void getPhoneByFireStation() {
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity pe1 = new PersonEntity();
        pe1.setFirstName("Damien");
        pe1.setLastName("Rouge");
        pe1.setAddress("1address1");
        pe1.setPhone("123456");
        peList.add(pe1);
        List<String> addressList = new ArrayList<>();
        addressList.add("1address1");
        when(fireStationService.getAddressByFireStation(1L)).thenReturn(addressList);
        when(personService.getPersonsByAddresses(addressList)).thenReturn(peList);

        List<String> phoneList = phoneAlertService.getPhoneByFireStation(1L);

        assertEquals("123456", phoneList.get(0));
    }
}