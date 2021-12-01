package com.safetynet.safetynetalert.ServiceTest;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.repository.PersonRepository;
import com.safetynet.safetynetalert.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private static final long MILLISECONDS_IN_YEAR = 31536000000L;

    @Mock
    private PersonRepository personRepository ;

    @InjectMocks
    private static PersonService personService;

    @Test
    public void addPersonTest() {
        when(personRepository.save(any())).thenReturn(new PersonEntity());
        personService.addPerson(new PersonEntity());
        verify(personRepository, times(1)).save(any());
    }

    @Test
    public void addPersonsTest() {
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity newPe1 = new PersonEntity();
        peList.add(newPe1);
        PersonEntity newPe2 = new PersonEntity();
        peList.add(newPe2);
        when(personRepository.saveAll(any())).thenReturn(peList);
        personService.addPersons(peList);
        verify(personRepository, times(1)).saveAll(any());
    }

    @Test
    public void getPersonsTest() {
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity newPe1 = new PersonEntity();
        peList.add(newPe1);
        when(personRepository.findAll()).thenReturn(peList);
        personService.getPersons();
        verify(personRepository,times(1)).findAll();
    }

    @Test
    public void getPersonsNoPersonsFoundTest() {
        when(personRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(NoSuchElementException.class,()-> personService.getPersons());
    }

    @Test
    public void getPersonByIdTest() {
        PersonEntity p = new PersonEntity();
        when(personRepository.findById(any()))
                .thenReturn(Optional.of(p));
        PersonEntity result = personService.getPersonById(2L);
        assertEquals(result, p);
    }

    @Test
    public void getPersonByIdNotFoundTest() {
        when(personRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> personService.getPersonById(1L));
    }

    @Test
    public void getPersonByNameTest() {
        PersonEntity pe = new PersonEntity();
        List<PersonEntity> peList = new ArrayList<>();
        peList.add(pe);
        when(personRepository.findPersonEntityByFirstNameAndLastName(any(),any()))
                .thenReturn(peList);
        personService.getPersonByName(any(),any());
        verify(personRepository,times(1))
                .findPersonEntityByFirstNameAndLastName(any(),any());
    }

    @Test
    public void getPersonByNameNotFoundTest() {
        when(personRepository.findPersonEntityByFirstNameAndLastName(any(),any()))
                .thenReturn(new ArrayList<>());
        assertThrows(NoSuchElementException.class,
                ()-> personService.getPersonByName(any(),any()));
    }

    @Test
    public void getPersonsByAddressTest() {
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity pe = new PersonEntity();
        peList.add(pe);
        when(personRepository.findPersonEntityByAddress(any()))
                .thenReturn(peList);
        personService.getPersonsByAddress("address");
        verify(personRepository,times(1))
                .findPersonEntityByAddress(any());
    }

    @Test
    public void getPersonsByAddressNoPersonFoundTest() {
        when(personRepository.findPersonEntityByAddress(any()))
                .thenReturn(new ArrayList<>());
        assertThrows(NoSuchElementException.class,
                ()-> personService.getPersonsByAddress(any()));
    }

    @Test
    public void getPersonsByAddressesTest() {
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity pe = new PersonEntity();
        peList.add(pe);
        when(personRepository.findAllByAddressIn(any())).thenReturn(peList);
        personService.getPersonsByAddresses(new ArrayList<>());
        verify(personRepository,times(1))
                .findAllByAddressIn(new ArrayList<>());
    }

    @Test
    public void getPersonsByAddressesNoPersonsFoundTest() {
        when(personRepository.findAllByAddressIn(any()))
                .thenReturn(new ArrayList<>());
       assertThrows(NoSuchElementException.class,() ->
               personService.getPersonsByAddresses(new ArrayList<>()));
    }

    @Test
    public void upDatePersonInfoTest() {
        PersonEntity newPe1 = new PersonEntity();
        newPe1.setFirstName("Damien");
        newPe1.setLastName("Care");
        newPe1.setId(1L);
        PersonEntity newPe2 = new PersonEntity();
        newPe2.setFirstName("Julie");
        newPe2.setLastName("Circle");
        newPe2.setCity("Tokyo");
        newPe2.setZip("51023");
        newPe2.setPhone("123456");
        newPe2.setEmail("lolilo@mail.com");
        newPe2.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(newPe1));
        PersonEntity pe = personService.upDatePersonInfo(newPe2);
        assertEquals("Damien", pe.getFirstName());
        assertEquals("Care", pe.getLastName());
        assertEquals("Tokyo", pe.getCity());
        assertEquals("51023", pe.getZip());
        assertEquals("123456", pe.getPhone());
        assertEquals("lolilo@mail.com", pe.getEmail());
        verify(personRepository,times(1)).save(newPe1);
    }

    @Test
    public void getPersonByCityTest(){
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity pe = new PersonEntity();
        peList.add(pe);
        when(personRepository.findPersonsEntityByCity(any())).thenReturn(peList);
        List<PersonEntity> cityPeList = personService.getPersonByCity("");
        assertEquals(1, cityPeList.size());
    }

    @Test
    public void getPersonByCityNoPersonFoundTest(){
        when(personRepository.findPersonsEntityByCity(any())).thenReturn(new ArrayList<>());
        assertThrows(NoSuchElementException.class, () -> personService.getPersonByCity(any()));
    }

    @Test
    public void deletePersonsByNameTest() {
        List<PersonEntity> peList = new ArrayList<>();
        PersonEntity pe1 = new PersonEntity();
        peList.add(pe1);
        PersonEntity pe2 = new PersonEntity();
        peList.add(pe2);
        when(personRepository.findPersonEntityByFirstNameAndLastName(any(),any()))
                .thenReturn(peList);
        doNothing().when(personRepository).delete(any());
        personService.deletePersonsByName("","");
        verify(personRepository,times(1))
                .findPersonEntityByFirstNameAndLastName(any(),any());
        verify(personRepository,times(2)).delete(any());
    }

    @Test
    public void deletePersonsByNameNoPersonFoundTest() {
        when(personRepository.findPersonEntityByFirstNameAndLastName(any(),any()))
                .thenReturn(new ArrayList<>());
        assertThrows(NoSuchElementException.class, () -> personService.deletePersonsByName("",""));
    }

    @Test
    public void deletePersonsByIdTest() {
        when(personRepository.findById(any()))
                .thenReturn(Optional.of(new PersonEntity()));
        personService.deletePersonById(1L);
        verify(personRepository,times(1)).delete(any());
    }

    @Test
    public void deletePersonsByIdNotFoundTest() {
        when(personRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> personService.getPersonById(any()));
    }

    @Test
    public void calculateAgeTest() {
        Date birthDate = new Date(System.currentTimeMillis()-(31536000000L*3));
        long age = personService.calculateAge(birthDate);
        assertEquals(2, age);
    }

    @Test
    public void calculateAgeLessOneYearTest() {
        Date birthDate = new Date(System.currentTimeMillis()-(3153600L));
        long age = personService.calculateAge(birthDate);
        assertEquals(0, age);
    }

    @Test
    public void majorPersonTrueTest() {
        PersonEntity newPe1 = new PersonEntity();
        newPe1.setFirstName("Damien");
        newPe1.setLastName("Care");
        Date birthDate = new Date(System.currentTimeMillis()-(MILLISECONDS_IN_YEAR * 20));
        newPe1.setBirthDate(birthDate);
        assertTrue(personService.majorPerson(newPe1));
    }

    @Test
    public void majorPersonFalseTest() {
        PersonEntity newPe1 = new PersonEntity();
        newPe1.setFirstName("Damien");
        newPe1.setLastName("Care");
        Date birthDate = new Date(System.currentTimeMillis()-(MILLISECONDS_IN_YEAR * 17));
        newPe1.setBirthDate(birthDate);
        assertFalse(personService.majorPerson(newPe1));
    }

    @Test
    public void majorPersonEqualEighteenTest() {
        PersonEntity newPe1 = new PersonEntity();
        newPe1.setFirstName("Damien");
        newPe1.setLastName("Care");
        Date birthDate = new Date(System.currentTimeMillis()-(MILLISECONDS_IN_YEAR * 19));
        newPe1.setBirthDate(birthDate);
        assertTrue(personService.majorPerson(newPe1));
    }

    @Test
    public void getPersonByMedicalRecordTest(){
        PersonEntity pe = new PersonEntity();
        MedicalRecordEntity mre = new MedicalRecordEntity();
        pe.setMedicalRecord(mre);
        when(personRepository.findByMedicalRecord(mre)).thenReturn(Optional.of(pe));
        personService.getPersonByMedicalRecord(mre);
        verify(personRepository,times(1)).findByMedicalRecord(mre);
    }

    @Test
    public void getPersonByMedicalRecordNoPersonFoundTest(){
        MedicalRecordEntity mre = new MedicalRecordEntity();
        when(personRepository.findByMedicalRecord(mre)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                ()-> personService.getPersonByMedicalRecord(mre));
        verify(personRepository,times(1)).findByMedicalRecord(mre);
    }
}
