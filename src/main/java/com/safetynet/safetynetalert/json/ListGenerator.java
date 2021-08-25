package com.safetynet.safetynetalert.json;


import com.jsoniter.any.Any;
import com.safetynet.safetynetalert.constant.DataJson;

import java.util.List;
import java.util.stream.Collectors;

public final class ListGenerator {
    private ListGenerator() {
    }
    private static Any any = DataJson.inputJson();
    /**
     * Generate a list with personJson objects from persons json file.
     * @return List personJson type
     */
    public static List<PersonJson> personsList() {
        return any.get("persons").asList()
                .stream()
                .map(any1 -> {
                    PersonJson pj = new PersonJson();
                    pj.setFirstName(any1.get("firstName").as(String.class));
                    pj.setLastName(any1.get("lastName").as(String.class));
                    pj.setAddress(any1.get("address").as(String.class));
                    pj.setCity(any1.get("city").as(String.class));
                    pj.setZip(any1.get("zip").as(String.class));
                    pj.setPhone(any1.get("phone").as(String.class));
                    pj.setEmail(any1.get("email").as(String.class));
                    return pj;
                })
                .collect(Collectors.toList());
    }

    /**
     * Generate a list with fireStation objects from firestations json file.
     * @return List fireStationJson type
     */
    public static List<FireStationJson> fireStationList() {
        return any.get("firestations").asList()
                .stream()
                .map(any1 -> {
                    FireStationJson fsj = new FireStationJson();
                    fsj.setAddress(any1.get("address").as(String.class));
                    fsj.setStation(any1.get("station").as(String.class));
                    return fsj;
                })
                .collect(Collectors.toList());
    }

    /**
     * Generate a list with medicalRecord objects from medicalrecords json file.
     * @return List MedicalRecordJson type
     */
    public static List<MedicalRecordJson> medicalRecordsList() {
        return any.get("medicalrecords").asList()
        .stream()
        .map(any1 -> {
            MedicalRecordJson mrj = new MedicalRecordJson();
            mrj.setFirstName(any1.get("firstName").as(String.class));
            mrj.setLastName(any1.get("lastName").as(String.class));
            mrj.setBirthdate(any1.get("birthdate").as(String.class));
            mrj.setMedications(any1.get("medications").
                    as(String[].class));
            mrj.setAllergies(any1.get("allergies").as(String[].class));
            return mrj;
        })
        .collect(Collectors.toList());
    }

}
