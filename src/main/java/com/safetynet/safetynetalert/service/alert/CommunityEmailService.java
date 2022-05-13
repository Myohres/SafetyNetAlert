package com.safetynet.safetynetalert.service.alert;

import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CommunityEmailService {

    /**
     * Person Service layer.
     */
    @Autowired
    private PersonService personService;

    /**
     * Get all persons List by city.
     * Collect Person email.
     * @param city String
     * @return Emails list
     * */
    public List<String> getEmailsCity(final String city)  {
        List<PersonEntity> peList = personService.getPersonByCity(city);
        List<String> emailList = new ArrayList<>();
        peList.forEach(personEntity ->
                emailList.add(personEntity.getEmail()));
        return emailList;
    }
}
