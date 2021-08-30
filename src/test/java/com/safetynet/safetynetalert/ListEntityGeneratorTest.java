package com.safetynet.safetynetalert;

import com.safetynet.safetynetalert.json.FireStationJson;
import com.safetynet.safetynetalert.json.MedicalRecordJson;
import com.safetynet.safetynetalert.json.PersonJson;
import com.safetynet.safetynetalert.entity.FireStationEntity;
import com.safetynet.safetynetalert.entity.ListEntityGenerator;
import com.safetynet.safetynetalert.entity.PersonEntity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ListEntityGeneratorTest {

    private static List<PersonJson> pjList;
    private static List<FireStationJson> fsjList;
    private static List<MedicalRecordJson> mrjList;
    private static ListEntityGenerator leGen;

    @BeforeAll
    static void initAll(){
        leGen = ListEntityGenerator.getInstance();
    }

    @BeforeEach
    private void setUpPerTest(){
        pjList = new ArrayList<>();
        fsjList = new ArrayList<>();
        mrjList = new ArrayList<>();
    }

    @Test
    public void personsEntityListTest() {
        PersonJson pj = new PersonJson();
        pj.setFirstName("Julien");
        pj.setLastName("Leroux");
        pj.setAddress("58 rue Lepotre");
        pj.setCity("Kyoto");
        pj.setZip("85200");
        pj.setPhone("123456");
        pj.setEmail("email@.com");
        pjList.add(pj);

        MedicalRecordJson mrj = new MedicalRecordJson();
        mrj.setFirstName("Julien");
        mrj.setLastName("Leroux");
        mrj.setBirthdate("04/18/1989");
        String[] medications = new String[2];
        medications[0] = "aznol:350mg";
        medications[1] = "thradox:700mg";
        mrj.setMedications(medications);
        String[] allergies = new String[2];
        allergies[0] = "peanut";
        allergies[1] = "shellfish";
        mrj.setAllergies(allergies);
        mrjList.add(mrj);

        List<PersonEntity> personEntityListTest = leGen.personsEntityList(pjList,mrjList);
        assertEquals("Julien",personEntityListTest.get(0).getFirstName());
        assertEquals("Leroux",personEntityListTest.get(0).getLastName());
        assertEquals("58 rue Lepotre",personEntityListTest.get(0).getAddress());
        assertEquals("Kyoto",personEntityListTest.get(0).getCity());
        assertEquals("85200",personEntityListTest.get(0).getZip());
        assertEquals("123456",personEntityListTest.get(0).getPhone());
        assertEquals("email@.com",personEntityListTest.get(0).getEmail());
        assertEquals("Tue Apr 18 00:00:00 CEST 1989",personEntityListTest.get(0).getBirthDate().toString());
        assertEquals("aznol:350mg",personEntityListTest.get(0).getMedicalRecord().getMedications().get(0));
        assertEquals("thradox:700mg",personEntityListTest.get(0).getMedicalRecord().getMedications().get(1));
        assertEquals("peanut",personEntityListTest.get(0).getMedicalRecord().getAllergies().get(0));
        assertEquals("shellfish",personEntityListTest.get(0).getMedicalRecord().getAllergies().get(1));
    }

    @Test
    public void personsEntityListTestWithNonCorrectFormatDate(){
        PersonJson pj = new PersonJson();
        pj.setFirstName("Arno");
        pj.setLastName("Leroux");
        pj.setAddress("58 rue Lepotre");
        pj.setCity("Kyoto");
        pj.setZip("85200");
        pj.setPhone("124562");
        pj.setEmail("email@.com");
        pjList.add(pj);

        MedicalRecordJson mrj = new MedicalRecordJson();
        mrj.setFirstName("Arno");
        mrj.setLastName("Leroux");
        mrj.setBirthdate("04-18-1989");
        String[] medications = new String[2];
        medications[0] = "aznol:350mg";
        medications[1] = "thradox:700mg";
        mrj.setMedications(medications);
        String[] allergies = new String[2];
        allergies[0] = "peanut";
        allergies[1] = "shellfish";
        mrj.setAllergies(allergies);
        mrjList.add(mrj);

        List<PersonEntity> personEntityListTest = leGen.personsEntityList(pjList,mrjList);
        assertNull(personEntityListTest.get(0).getBirthDate());

    }

    @Test
    public void fireStationEntityListTest() {
        FireStationJson fsj1 = new FireStationJson();
        fsj1.setAddress("834 Binoc Ave");
        fsj1.setStation("1");
        fsjList.add(fsj1);
        FireStationJson fsj2 = new FireStationJson();
        fsj2.setAddress("489 Manchester St");
        fsj2.setStation("2");
        fsjList.add(fsj2);
        FireStationJson fsj3 = new FireStationJson();
        fsj3.setAddress("834 Binoc Ave");
        fsj3.setStation("3");
        fsjList.add(fsj3);
        FireStationJson fsj4 = new FireStationJson();
        fsj4.setAddress("112 Steppes Pl");
        fsj4.setStation("1");
        fsjList.add(fsj4);
        List<FireStationEntity> fireStationEntityListTest = leGen.fireStationEntityList(fsjList);
        assertEquals(3,fireStationEntityListTest.size());
        assertEquals(1, fireStationEntityListTest.get(0).getStation());
        assertEquals("834 Binoc Ave",fireStationEntityListTest.get(0).getAddress().get(0));
        assertEquals("112 Steppes Pl",fireStationEntityListTest.get(0).getAddress().get(1));
    }
}
