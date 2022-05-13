package com.safetynet.safetynetalert.ServiceTest.alert;

import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.PersonService;
import com.safetynet.safetynetalert.service.alert.CommunityEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommunityEmailServiceTest {

    @InjectMocks
    private CommunityEmailService communityEmailService;

    @Mock
    private PersonService personService;

    @Test
    void getEmailsCity() {
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity pe1 = new PersonEntity();
        pe1.setFirstName("Damien");
        pe1.setLastName("Rouge");
        pe1.setCity("Tokyo");
        pe1.setEmail("email@mail.com");
        peList.add(pe1);
        PersonEntity pe2 = new PersonEntity();
        pe2.setFirstName("Julie");
        pe2.setLastName("Rert");
        pe2.setCity("Tokyo");
        pe2.setEmail("email2@mail.com");
        peList.add(pe2);
        when(personService.getPersonByCity("Tokyo")).thenReturn(peList);
        List<String> mailList = communityEmailService.getEmailsCity("Tokyo");
        assertEquals(2, mailList.size());
    }
}