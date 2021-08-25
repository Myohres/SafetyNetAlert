package com.safetynet.safetynetalert;

import com.jsoniter.any.Any;
import com.safetynet.safetynetalert.constant.DataJson;
import com.safetynet.safetynetalert.json.FireStationJson;
import com.safetynet.safetynetalert.json.ListGenerator;
import com.safetynet.safetynetalert.json.MedicalRecordJson;
import com.safetynet.safetynetalert.json.PersonJson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SafetyNetAlertApplication implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication.run(SafetyNetAlertApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        // Extraire les données JSON
        Any jsonInput = DataJson.inputJson();
        if (jsonInput != null) {
            List<PersonJson> personJsonList =
                    ListGenerator.personsList(jsonInput);
            List<FireStationJson> fireStationJsons =
                    ListGenerator.fireStationList(jsonInput);
            List<MedicalRecordJson> medicalRecordJsons =
                    ListGenerator.medicalRecordsList(jsonInput);
        }
        // Modéliser ces données
        // Insérer les données dans la BDD
    }
}
