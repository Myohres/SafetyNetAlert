package com.safetynet.safetynetalert.ServiceTest;

import com.safetynet.safetynetalert.entity.FireStationEntity;
import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.FireStationService;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.service.URLService;
import com.safetynet.safetynetalert.web.dto.groupProfil.FamilyByAddressDTO;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsByFireStationAddressesDTO;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsWhenFireDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonFullDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNamePhoneAgeMedical;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class URLServiceTest {


    @InjectMocks
    private URLService urlService;
    @Mock
    private PersonService personService;
    @Mock
    private FireStationService fireStationService;

    @Test
    void getPersonByFireStationWithChildAndAdult() {
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

        PersonsByFireStationAddressesDTO persons = urlService.getPersonByFireStation(1);

        assertEquals(1, persons.getNbAdult());
        assertEquals(1, persons.getNbChild());
        assertEquals(2, persons.getPersons().size());
    }

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

        FamilyByAddressDTO family = urlService.getFamilyByAddress("1address1");

        assertEquals(1, family.getAdultList().size());
        assertEquals("Damien", family.getAdultList().get(0).getFirstName());
        assertEquals(1, family.getChildList().size());
        assertEquals(9, family.getChildList().get(0).getAge());
    }

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

        List<String> phoneList = urlService.getPhoneByFireStation(1L);

        assertEquals("123456", phoneList.get(0));
    }

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

        PersonsWhenFireDTO persons = urlService.getPersonsWhenFireByAddress("1address1");

        assertEquals(2,persons.getPersons().size());
        assertEquals("Damien", persons.getPersons().get(0).getFirstName());
        assertEquals("Rouge", persons.getPersons().get(1).getLastName());
        assertEquals("123456",persons.getPersons().get(0).getPhone());
        assertEquals("allergy2", persons.getPersons().get(1).getMedicalRecord().getAllergies().get(0));
        assertEquals(9, persons.getPersons().get(1).getAge());
    }

    @Test
    void getFoyersByFireStation() {
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

        Map<String, List<PersonNamePhoneAgeMedical>> personsList = urlService.getFoyersByFireStations(stationList);

        assertEquals("Julie",personsList.get("2address1").get(0).getFirstName());
        assertEquals("Rouge",personsList.get("1address1").get(0).getLastName());
        assertEquals("123456",personsList.get("1address1").get(0).getPhone());
        assertEquals(29,personsList.get("2address1").get(0).getAge());
        assertEquals("medication1",personsList.get("1address1").get(0).getMedicalRecord().getMedications().get(0));
    }

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

        List<PersonFullDTO> personList = urlService.getPersonInfo("Damien", "Rouge");

        assertEquals("Damien", personList.get(0).getFirstName());
        assertEquals("Rouge", personList.get(0).getLastName());
        assertEquals("1address1", personList.get(0).getAddress());
        assertEquals("Tokyo",personList.get(0).getCity());
        assertEquals("51200", personList.get(0).getZip());
        assertEquals(19, personList.get(0).getAge());
        assertEquals("email@mail.com", personList.get(0).getEmail());
        assertEquals("medication1",personList.get(0).getMedicalRecord().getMedications().get(0));
    }

    @Test
    void getEmailsCity() {
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity pe1 = new PersonEntity();
        pe1.setFirstName("Damien");
        pe1.setLastName("Rouge");
        pe1.setCity("Tokyo");
        pe1.setEmail("email@mail.com");
        peList.add(pe1);
        PersonEntity pe2 = new PersonEntity();
        pe2.setFirstName("Julie");
        pe2.setLastName("Rert");
        pe2.setCity("Tokyo");
        pe2.setEmail("email2@mail.com");
        peList.add(pe2);
        when(personService.getPersonByCity("Tokyo")).thenReturn(peList);
        List<String> mailList = urlService.getEmailsCity("Tokyo");
        assertEquals(2, mailList.size());

    }
}