package com.safetynet.safetynetalert.entity;

import com.safetynet.safetynetalert.json.FireStationJson;
import com.safetynet.safetynetalert.json.MedicalRecordJson;
import com.safetynet.safetynetalert.json.PersonJson;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class ListEntityGenerator {

    /**
     * Generate a list with PersonEntity type.
     * @param pjList List<PersonJson>
     * @param mrjList List<MedicalRecordJson>
     * @return List<PersonEntity>
     */
    public List<PersonEntity> personsEntityList(
            final List<PersonJson> pjList,
            final List<MedicalRecordJson> mrjList) {
        return pjList.stream()
                .map(any1 -> {
                    PersonEntity pe = new PersonEntity();
                    pe.setFirstName(any1.getFirstName());
                    pe.setLastName(any1.getLastName());
                    pe.setAddress(any1.getAddress());
                    pe.setCity(any1.getCity());
                    pe.setZip(any1.getZip());
                    pe.setPhone(any1.getPhone());
                    pe.setEmail(any1.getEmail());
                    MedicalRecordEntity mre = new MedicalRecordEntity();
                    pe.setMedicalRecord(mre);
                    for (MedicalRecordJson mrj: mrjList
                    ) {
                        if (mrj.getLastName().equals(any1.getLastName())
                                && (mrj.getFirstName()
                                .equals(any1.getFirstName()))) {
                            Date newBirthDate = null;
                            try {
                                newBirthDate = new SimpleDateFormat(
                                        "MM/dd/yyyy").parse(mrj.getBirthdate());
                                pe.setBirthDate(newBirthDate);
                            } catch (ParseException e) {
                                System.err.println("Invalid date format : "
                                        + mrj.getBirthdate());
                            } finally {
                                pe.setBirthDate(newBirthDate);
                                mre.setAllergies(
                                        Arrays.asList(mrj.getAllergies()));
                                mre.setMedications(
                                        Arrays.asList(mrj.getMedications()));
                                pe.setMedicalRecord(mre);
                            }
                        }
                    }
                    return pe;
                }).collect(Collectors.toList());
    }

    /**
     * Generate a list with FireStationEntity type.
     * collect all stations and distinct them
     * add address next
     * @param fsjList List<FireStationJson>
     * @return List<FireStationEntity>
     */
    public List<FireStationEntity> fireStationEntityList(
            final List<FireStationJson> fsjList) {

        List<FireStationEntity> fireStationEntityList = fsjList
                .stream()
                .map(fireStationJson -> {
                    FireStationEntity fse = new FireStationEntity();
                    fse.setStation(fireStationJson.getStation());
                    return fse;
                }).distinct().collect(Collectors.toList());
        fireStationEntityList.forEach(fireStationEntity -> {
            fireStationEntity.setAddress(fsjList.stream()
                    .filter(fireStationJson ->
                            Long.valueOf(fireStationJson.getStation())
                                    .equals(fireStationEntity.getStation()))
                    .map(FireStationJson::getAddress)
                    .collect(Collectors.toList()));
        });

        return fireStationEntityList;
    }
}
