package com.safetynet.safetynetalert;

import com.jsoniter.any.Any;
import com.safetynet.safetynetalert.constant.DataJson;
import com.safetynet.safetynetalert.entity.FireStationEntity;
import com.safetynet.safetynetalert.entity.ListEntityGenerator;
import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.json.FireStationJson;
import com.safetynet.safetynetalert.json.ListGenerator;
import com.safetynet.safetynetalert.json.MedicalRecordJson;
import com.safetynet.safetynetalert.json.PersonJson;
import com.safetynet.safetynetalert.service.FireStationService;
import com.safetynet.safetynetalert.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class SafetyNetAlertApplication implements CommandLineRunner {
    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private ListEntityGenerator leGen;

    public static void main(final String[] args) {
        SpringApplication.run(SafetyNetAlertApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        Any any = DataJson.inputJson();

        //List Generator Json
        List<PersonJson> pjList =
                ListGenerator.personsList(Objects.requireNonNull(any));
        List<MedicalRecordJson> mrjList =
                ListGenerator.medicalRecordsList(any);
        List<FireStationJson> fsjList =
                ListGenerator.fireStationList(any);

        // List Generator entity
        List<PersonEntity> personEntityList =
                leGen.personsEntityList(pjList, mrjList);
        List<FireStationEntity> fireStationEntityList =
                leGen.fireStationEntityList(fsjList);

        // Sending List to BDD
        personService.addPersons(personEntityList);
        fireStationEntityList.forEach(fireStationEntity
                -> fireStationService.addFireStation(fireStationEntity));
    }
}
