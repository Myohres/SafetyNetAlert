package com.safetynet.safetynetalert.service;

import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Date;


@Service
public class PersonService {
    /** Repository link for PersonEntity.*/
    @Autowired
    private PersonRepository personRepository;

    /**
     * add PersonEntity to BDD.
     * @param personEntity PersonEntity
     * @return save PersonEntity to BDD
     */
    public PersonEntity addPerson(final PersonEntity personEntity) {
        return personRepository.save(personEntity);
    }

    /**
     * Add a PersonEntityList to BDD.
     * @param persons List<PersonEntity>
     * @return List<PersonEntity>
     */
    public List<PersonEntity> addPersons(final List<PersonEntity> persons) {
        return  personRepository.saveAll(persons);
    }

    /**
     * Get all persons from BDD.
     * @return list persons
     */
    public List<PersonEntity> getPersons() {
        return personRepository.findAll();
    }
    /**
     * Get one person with his ID from BDD.
     * @param id long
     * @return person
     */
    public PersonEntity getPersonById(final Long id)
            throws NoSuchElementException {
        Optional<PersonEntity> optionalPersonEntity =
                personRepository.findById(id);
        return optionalPersonEntity.orElseThrow(()
                -> new NoSuchElementException("No person with id " + id));
    }

    /**
     * Get Persons by Name in BDD.
     * @param lastName String
     * @param firstName String
     * @return List<PersonEntity>
     */
    public List<PersonEntity> getPersonByName(
            final String lastName,
            final String firstName) {
        return personRepository
                .findPersonEntityByFirstNameAndLastName(
                        firstName, lastName);
    }

    /**
     * Get Persons by one address in BDD.
     * @param address String
     * @return List<PersonEntity>
     */
    public List<PersonEntity> getPersonsByAddress(
            final String address) {
        return personRepository.findPersonEntityByAddress(address);
    }

    /**
     * Get Persons by a address List in BDD.
     * @param addresses String
     * @return List<PersonEntity>
     */
    public List<PersonEntity> getPersonsByAddresses(
            final List<String> addresses) {
        return personRepository.findAllByAddressIn(addresses);
    }

    /**
     * Update Person information.
     * @param personNewInfos PersonEntity
     * @return saving PersonEntity updated
     * @throws NoSuchElementException
     */
    public PersonEntity upDatePersonInfo(final PersonEntity personNewInfos)
            throws NoSuchElementException {
        long id = personNewInfos.getId();
        PersonEntity pe = getPersonById(id);
        pe.setAddress(personNewInfos.getAddress());
        pe.setCity(personNewInfos.getCity());
        pe.setZip(personNewInfos.getZip());
        pe.setPhone(personNewInfos.getPhone());
        pe.setEmail(personNewInfos.getEmail());
        pe.setBirthDate(personNewInfos.getBirthDate());
        pe.setMedicalRecord(personNewInfos.getMedicalRecord());
        return personRepository.save(pe);
    }

    /**
     * Delete Persons by Name.
     * @param lastName String
     * @param firstName String
     */
    public void deletePersonsByName(
            final String lastName, final String firstName) {
       List<PersonEntity> peList = personRepository
               .findPersonEntityByFirstNameAndLastName(firstName, lastName);
       peList.forEach(personEntity -> personRepository.delete(personEntity));

    }

    /**
     * Delete Persons by id.
     * @param id long
     */
    public void deletePersonById(final long id) {
        personRepository.delete(getPersonById(id));
    }

    /**
     * Calculate age person from his birthdate.
     * @param birthDate Date
     * @return long age
     */
    public long getAge(final Date birthDate) {
        LocalDate localBirthDate =  birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate =  LocalDate.now(ZoneId.systemDefault());
        return ChronoUnit.YEARS.between(localBirthDate, currentDate);
    }

    /**
     * Determinate majority person.
     * @param personEntity PersonEntity
     * @return true major false minor
     */
    public boolean majorPerson(final PersonEntity personEntity) {
        LocalDate localBirthDate = personEntity.getBirthDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate =  LocalDate.now(ZoneId.systemDefault());
        long personYears =
                ChronoUnit.YEARS.between(localBirthDate, currentDate);
        final long majorAge = 18;
        return personYears > majorAge;
    }

}
