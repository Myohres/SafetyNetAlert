package com.safetynet.safetynetalert.service.alert;

import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.web.dto.personProfil.MedicalRecordDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonFullDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersonInfoService {

    /**
     * Person Service layer.
     */
    @Autowired
    private PersonService personService;

    /**
     * Get persons List by Name.
     * Send personsEntity to personDTO
     * @param firstName String
     * @param lastName String
     * @return persons DTO list
     */
    public List<PersonFullDTO> getPersonInfo(
            final String firstName, final String lastName) {
        return personService.getPersonByName(lastName, firstName)
                .stream()
                .map(personEntity -> {
                    PersonFullDTO peDTO = new PersonFullDTO();
                    peDTO.setLastName(personEntity.getLastName());
                    peDTO.setFirstName(personEntity.getFirstName());
                    peDTO.setAddress(personEntity.getAddress());
                    peDTO.setCity(personEntity.getCity());
                    peDTO.setZip(personEntity.getZip());
                    peDTO.setAge(personEntity.getBirthDate());
                    peDTO.setEmail(personEntity.getEmail());
                    MedicalRecordDTO mrDTO = new MedicalRecordDTO();
                    mrDTO.setAllergies(personEntity.
                            getMedicalRecord().getAllergies());
                    mrDTO.setMedications(
                            personEntity.getMedicalRecord().getMedications());
                    peDTO.setMedicalRecord(mrDTO);
                    return peDTO;
                }).collect(Collectors.toList());
    }
}
