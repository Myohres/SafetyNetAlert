package com.safetynet.safetynetalert;


import com.jsoniter.any.Any;
import com.safetynet.safetynetalert.config.DataJsonTest;
import com.safetynet.safetynetalert.json.FireStationJson;
import com.safetynet.safetynetalert.json.ListGenerator;
import com.safetynet.safetynetalert.json.MedicalRecordJson;
import com.safetynet.safetynetalert.json.PersonJson;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ListGeneratorTest {


    Any any = DataJsonTest.inputJson();


    @Test
    public void listPersonJsonGeneratedWithAllPerson() {
        List<PersonJson> personTest = ListGenerator.personsList();
        int sizePersonTest = personTest.size();
        int sizeJsonTest = any.get("persons").size();
        assertEquals(sizePersonTest, sizeJsonTest);
    }

    @Test
    public void listPersonJsonGeneratedWithPersonData() {
        List<PersonJson> personTest = ListGenerator.personsList();
        Any person2 = any.get("persons").get(1);
        assertEquals(person2.get("firstName").as(String.class), personTest.get(1).getFirstName());
        assertEquals(person2.get("lastName").as(String.class), personTest.get(1).getLastName());
        assertEquals(person2.get("address").as(String.class), personTest.get(1).getAddress());
        assertEquals(person2.get("city").as(String.class), personTest.get(1).getCity());
        assertEquals(person2.get("zip").as(String.class), personTest.get(1).getZip());
        assertEquals(person2.get("phone").as(String.class), personTest.get(1).getPhone());
        assertEquals(person2.get("email").as(String.class), personTest.get(1).getEmail());
    }


    @Test
    public void listFireStationJsonGenerated() {
        List<FireStationJson> fireStationTest = ListGenerator.fireStationList();
        int sizeFireStationTest = fireStationTest.size();
        int sizeJsonTest = any.get("firestations").size();
        assertEquals(sizeFireStationTest, sizeJsonTest);

    }

    @Test
    public void listFireStationJsonGeneratedWithFireStationsData() {
        List<FireStationJson> fireStationTest = ListGenerator.fireStationList();
        Any fireStation2 = any.get("firestations").get(1);
        assertEquals(fireStation2.get("station").as(String.class), fireStationTest.get(1).getStation());
        assertEquals(fireStation2.get("address").as(String.class), fireStationTest.get(1).getAddress());

    }

    @Test
    public void listMedicalRecordGenerated() {
        List<MedicalRecordJson> medicalRecordTest = ListGenerator.medicalRecordsList();
        int sizeMedicalRecordTest = medicalRecordTest.size();
        int sizeJsonTest = any.get("medicalrecords").size();
        assertEquals(sizeMedicalRecordTest, sizeJsonTest);

    }

    @Test
    public void listMedicalRecordGeneratedWithMedicalRecordsData() {
        List<MedicalRecordJson> medicalRecordTest = ListGenerator.medicalRecordsList();
        Any medicalRecord2 = any.get("medicalrecords").get(1);
        assertEquals(medicalRecord2.get("firstName").as(String.class), medicalRecordTest.get(1).getFirstName());
        assertEquals(medicalRecord2.get("lastName").as(String.class), medicalRecordTest.get(1).getLastName());
        assertEquals(medicalRecord2.get("birthdate").as(String.class), medicalRecordTest.get(1).getBirthdate());
        //TODO find solution for get String[]
     /* assertEquals(medicalRecord2.get("medications").get(String[].class), medicalRecordTest.get(1).getMedications());
        assertEquals(medicalRecord2.get("allergies").get(String[].class), medicalRecordTest.get(1).getAllergies());*/
    }
}