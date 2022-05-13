package com.safetynet.safetynetalert.ServiceTest.alert;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.service.alert.PersonInfoService;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonFullDTO;
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
class PersonInfoServiceTest {

    @InjectMocks
    private PersonInfoService personInfoService;

    @Mock
    private PersonService personService;

    @Test
    void getPersonInfo() {
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity pe1 = new PersonEntity();
        pe1.setFirstName("Damien");
        pe1.setLastName("Rouge");
        pe1.setPhone("123456");
        pe1.setAddress("1address1");
        pe1.setCity("Tokyo");
        pe1.setZip("51200");
        pe1.setEmail("email@mail.com");
        pe1.setBirthDate(new Date(System.currentTimeMillis()-(31536000000L*20)));
        MedicalRecordEntity mre1 = new MedicalRecordEntity();
        List<String> medication1 = new ArrayList<>();
        medication1.add("medication1");
        mre1.setMedications(medication1);
        pe1.setMedicalRecord(mre1);
        peList.add(pe1);
        when(personService.getPersonByName("Rouge","Damien")).thenReturn(peList);

        List<PersonFullDTO> personList = personInfoService.getPersonInfo("Damien", "Rouge");

        assertEquals("Damien", personList.get(0).getFirstName());
        assertEquals("Rouge", personList.get(0).getLastName());
        assertEquals("1address1", personList.get(0).getAddress());
        assertEquals("Tokyo",personList.get(0).getCity());
        assertEquals("51200", personList.get(0).getZip());
        assertEquals(19, personList.get(0).getAge());
        assertEquals("email@mail.com", personList.get(0).getEmail());
        assertEquals("medication1",personList.get(0).getMedicalRecord().getMedications().get(0));
    }
}