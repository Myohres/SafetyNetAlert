package com.safetynet.safetynetalert;


import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.safetynetalert.json.FireStationJson;
import com.safetynet.safetynetalert.json.ListGenerator;
import com.safetynet.safetynetalert.json.MedicalRecordJson;
import com.safetynet.safetynetalert.json.PersonJson;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ListGeneratorTest {

    @Test
    public void listPersonJsonGenerated() {
        String str = "{\"persons\": [" +
                "{ \"firstName\":\"John\",\"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\"," +
                " \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }" +
                "]}";
        Any jsonInput = JsonIterator.deserialize(str);
        List<PersonJson> personTest = ListGenerator.personsList(jsonInput);
        assertEquals("John",personTest.get(0).getFirstName());
        assertEquals("Boyd",personTest.get(0).getLastName());
        assertEquals("1509 Culver St", personTest.get(0).getAddress());
        assertEquals("Culver", personTest.get(0).getCity());
        assertEquals("97451", personTest.get(0).getZip());
        assertEquals("841-874-6512", personTest.get(0).getPhone());
        assertEquals("jaboyd@email.com", personTest.get(0).getEmail());
    }

    @Test
    public void listPersonJsonGeneratedWithMultiplePerson() {
        String str = "{\"persons\": [" +
                "{ \"firstName\":\"John\",\"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\"," +
                " \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }," +
                "{ \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\"," +
                " \"zip\":\"97451\", \"phone\":\"841-874-6513\", \"email\":\"drk@email.com\" }" +
                "]}";
        Any jsonInput = JsonIterator.deserialize(str);
        List<PersonJson> personTest = ListGenerator.personsList(jsonInput);
        assertEquals(2, personTest.size());
    }

    @Test
    public void listFireStationJsonGenerated() {
        String str = "{\"firestations\": [" +
                "{ \"address\":\"1509 Culver St\", \"station\":\"3\" }"+
                "]}";
        Any jsonInput = JsonIterator.deserialize(str);
        List<FireStationJson> fireStationTest = ListGenerator.fireStationList(jsonInput);
        assertEquals("3", fireStationTest.get(0).getStation());
        assertEquals("1509 Culver St", fireStationTest.get(0).getAddress());
    }

    @Test
    public void listFireStationJsonGeneratedWidthMultipleStation() {
        String str = "{\"firestations\": [" +
                "{ \"address\":\"1509 Culver St\", \"station\":\"3\" },"+
                "{ \"address\":\"29 15th St\", \"station\":\"2\" }" +
                "]}";
        Any jsonInput = JsonIterator.deserialize(str);
        List<FireStationJson> fireStationTest = ListGenerator.fireStationList(jsonInput);
        assertEquals(2, fireStationTest.size());
    }

    @Test
    public void listMedicalRecordGenerated() {
        String str = "{\"medicalrecords\": ["+
                "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", " +
                "\"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }"+
                "]}";

        Any jsonInput = JsonIterator.deserialize(str);
        List<MedicalRecordJson> medicalRecordTest = ListGenerator.medicalRecordsList(jsonInput);
        assertEquals("John", medicalRecordTest.get(0).getFirstName());
        assertEquals("Boyd", medicalRecordTest.get(0).getLastName());
        assertEquals("03/06/1984", medicalRecordTest.get(0).getBirthdate());
        assertEquals("aznol:350mg", (medicalRecordTest.get(0).getMedications())[0]);
        assertEquals("hydrapermazol:100mg", (medicalRecordTest.get(0).getMedications())[1]);
        assertEquals("nillacilan", (medicalRecordTest.get(0).getAllergies())[0]);
    }

    @Test
    public void listMedicalRecordGeneratedWithMultipleMedicalRecords() {
        String str = "{\"medicalrecords\": ["+
                "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] },"+
                "{ \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1989\", \"medications\":[\"pharmacol:5000mg\", \"terazine:10mg\", \"noznazol:250mg\"], \"allergies\":[] }"+
                "]}";
        Any jsonInput = JsonIterator.deserialize(str);
        List<MedicalRecordJson> medicalRecordTest = ListGenerator.medicalRecordsList(jsonInput);
        assertEquals(2, medicalRecordTest.size());
    }
}