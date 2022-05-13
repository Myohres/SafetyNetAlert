package com.safetynet.safetynetalert.ServiceTest.alert;

import com.safetynet.safetynetalert.entity.FireStationEntity;
import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.FireStationService;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.service.alert.FireService;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsWhenFireDTO;
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
class FireServiceTest {

    @InjectMocks
    private FireService fireService;

    @Mock
    private PersonService personService;

    @Mock
    private FireStationService fireStationService;

    @Test
    void getPersonsWhenFireByAddress() {
        List<PersonEntity> peList = new ArrayList<>();

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
        peList.add(pe1);

        PersonEntity pe2 = new PersonEntity();
        pe2.setFirstName("Julie");
        pe2.setLastName("Rouge");
        pe2.setAddress("1address1");
        pe2.setPhone("123456");
        pe2.setBirthDate(new Date(System.currentTimeMillis()-(31536000000L*10)));
        MedicalRecordEntity mre2 = new MedicalRecordEntity();
        List<String> allergy2 = new ArrayList<>();
        allergy2.add("allergy2");
        mre2.setAllergies(allergy2);
        pe2.setMedicalRecord(mre2);
        peList.add(pe2);

        List<FireStationEntity> fseList = new ArrayList<>();
        FireStationEntity fse = new FireStationEntity();
        List<String> addressList = new ArrayList<>();
        addressList.add("1address1");
        fse.setAddress(addressList);
        fseList.add(fse);

        when(fireStationService.getFireStationByAddress("1address1")).thenReturn(fseList);
        when(personService.getPersonsByAddress("1address1")).thenReturn(peList);

        PersonsWhenFireDTO persons = fireService.getPersonsWhenFireByAddress("1address1");

        assertEquals(2,persons.getPersons().size());
        assertEquals("Damien", persons.getPersons().get(0).getFirstName());
        assertEquals("Rouge", persons.getPersons().get(1).getLastName());
        assertEquals("123456",persons.getPersons().get(0).getPhone());
        assertEquals("allergy2", persons.getPersons().get(1).getMedicalRecord().getAllergies().get(0));
        assertEquals(9, persons.getPersons().get(1).getAge());
    }
}