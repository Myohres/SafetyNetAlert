package com.safetynet.safetynetalert.ServiceTest.alert;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.FireStationService;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.service.alert.FloodService;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNamePhoneAgeMedical;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FloodServiceTest {

    @InjectMocks
    private FloodService floodService;

    @Mock
    private PersonService personService;

    @Mock
    private FireStationService fireStationService;

    @Test
    void getFoyersByFireStations() {
        List<PersonEntity> peList1 = new ArrayList<>();
        PersonEntity pe1 = new PersonEntity();
        pe1.setFirstName("Damien");
        pe1.setLastName("Rouge");
        pe1.setPhone("123456");
        pe1.setAddress("1address1");
        pe1.setBirthDate(new Date(System.currentTimeMillis()-(31536000000L*20)));
        MedicalRecordEntity mre1 = new MedicalRecordEntity();
        List<String> medication1 = new ArrayList<>();
        medication1.add("medication1");
        mre1.setMedications(medication1);
        pe1.setMedicalRecord(mre1);
        peList1.add(pe1);

        List<PersonEntity> peList2 = new ArrayList<>();
        PersonEntity pe2 = new PersonEntity();
        pe2.setFirstName("Julie");
        pe2.setLastName("Rouge");
        pe2.setAddress("2address1");
        pe2.setPhone("789123");
        pe2.setBirthDate(new Date(System.currentTimeMillis()-(31536000000L*30)));
        MedicalRecordEntity mre2 = new MedicalRecordEntity();
        List<String> allergy2 = new ArrayList<>();
        allergy2.add("allergy2");
        mre2.setAllergies(allergy2);
        pe2.setMedicalRecord(mre2);
        peList2.add(pe2);

        List<String> allAddressList = new ArrayList<>();
        allAddressList.add("1address1");
        allAddressList.add("2address1");

        List<Long> stationList = new ArrayList<>();
        stationList.add(1L);
        stationList.add(2L);

        when(fireStationService.getAddressByFireStations(stationList)).thenReturn(allAddressList);
        when(personService.getPersonsByAddress("1address1")).thenReturn(peList1);
        when(personService.getPersonsByAddress("2address1")).thenReturn(peList2);

        Map<String, List<PersonNamePhoneAgeMedical>> personsList = floodService.getFoyersByFireStations(stationList);

        assertEquals("Julie",personsList.get("2address1").get(0).getFirstName());
        assertEquals("Rouge",personsList.get("1address1").get(0).getLastName());
        assertEquals("123456",personsList.get("1address1").get(0).getPhone());
        assertEquals(29,personsList.get("2address1").get(0).getAge());
        assertEquals("medication1",personsList.get("1address1").get(0).getMedicalRecord().getMedications().get(0));
    }
}