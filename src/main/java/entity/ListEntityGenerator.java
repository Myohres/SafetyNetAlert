package entity;

import com.safetynet.safetynetalert.json.FireStationJson;
import com.safetynet.safetynetalert.json.ListGenerator;
import com.safetynet.safetynetalert.json.MedicalRecordJson;
import com.safetynet.safetynetalert.json.PersonJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ListEntityGenerator {

    public static List<PersonEntity> personsEntityList() {
        List<PersonJson> personJsonList = ListGenerator.personsList();
        List<MedicalRecordJson> mrjs = ListGenerator.medicalRecordsList();
        return personJsonList.stream()
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
                    for (MedicalRecordJson mrj: mrjs
                    ) {
                        if (mrj.getLastName().equals(any1.getLastName())
                                && (mrj.getFirstName()
                                .equals(any1.getFirstName()))) {
                            try {
                                Date newBirthDate = new SimpleDateFormat(
                                        "MM/dd/yyyy").parse(mrj.getBirthdate());
                                pe.setBirthDate(newBirthDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            mre.setAllergies(Arrays.asList(mrj.getAllergies()));
                            mre.setMedications(
                                    Arrays.asList(mrj.getMedications()));
                        }
                    }
                    return pe;
                }).collect(Collectors.toList());
    }

    public static List<FireStationEntity> fireStationEntityList() {
        List<FireStationJson> fsjList = ListGenerator.fireStationList();
        TreeMap<String, List<String>> listATrier = new TreeMap<>();
        List<FireStationEntity> fireStationEntityList = new ArrayList<>();
        for (FireStationJson fsj: fsjList
        ) {
            if (!(listATrier.containsKey(fsj.getStation()))) {
                List<String> addressNew = new ArrayList<>();
                addressNew.add(fsj.getAddress());
                listATrier.put(fsj.getStation(), addressNew);
            } else if (listATrier.containsKey(fsj.getStation())) {
                listATrier.get(fsj.getStation()).add(fsj.getAddress());
            }
        }
        for (final Map.Entry<String,
                List<String>> entry : listATrier.entrySet()) {
            FireStationEntity fse = new FireStationEntity();
            final String key = entry.getKey();
            final List<String> value = entry.getValue();
            fse.setStation(key);
            fse.setAddress(value);
            fireStationEntityList.add(fse);
        }
        return fireStationEntityList;
    }
}
