package com.safetynet.safetynetalert.ServiceTest.alert;

import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.FireStationService;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.service.alert.ChildAlertService;
import com.safetynet.safetynetalert.web.dto.groupProfil.FamilyByAddressDTO;
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
class ChildAlertServiceTest {

    @InjectMocks
    private ChildAlertService childAlertService;

    @Mock
    private PersonService personService;

    @Test
    void getFamilyByAddress() {
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity pe1 = new PersonEntity();
        pe1.setFirstName("Damien");
        pe1.setLastName("Rouge");
        pe1.setAddress("1address1");
        pe1.setBirthDate(new Date(System.currentTimeMillis()-(31536000000L*20)));
        peList.add(pe1);
        PersonEntity pe2 = new PersonEntity();
        pe2.setFirstName("Julie");
        pe2.setLastName("Rouge");
        pe2.setAddress("1address1");
        pe2.setBirthDate(new Date(System.currentTimeMillis()-(31536000000L*10)));
        peList.add(pe2);
        when(personService.getPersonsByAddress("1address1")).thenReturn(peList);
        when((personService.majorPerson(pe1))).thenReturn(true);
        when((personService.majorPerson(pe2))).thenReturn(false);

        FamilyByAddressDTO family = childAlertService.getFamilyByAddress("1address1");

        assertEquals(1, family.getAdultList().size());
        assertEquals("Damien", family.getAdultList().get(0).getFirstName());
        assertEquals(1, family.getChildList().size());
        assertEquals(9, family.getChildList().get(0).getAge());
    }
}