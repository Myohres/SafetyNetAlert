package com.safetynet.safetynetalert.service;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
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
    public List<PersonEntity> getPersons()
            throws NoSuchElementException {
        List<PersonEntity> peList = personRepository.findAll();
        if (peList.isEmpty()) {
            throw  new NoSuchElementException("No person found");
        }
        return peList;
    }

    /**
     * Get one person with his ID from BDD.
     * @param id Person id
     * @return person
     * @throws NoSuchElementException If id does not exist
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
            final String firstName)
            throws NoSuchElementException {
        List<PersonEntity> peList =
                personRepository.findPersonEntityByFirstNameAndLastName(
                        firstName, lastName);
        if (peList.isEmpty()) {
            throw  new NoSuchElementException(
                    "No person found with name : "
                            + firstName + " " + lastName);
        }
        return  peList;

    }
    /**
     * Get Persons by one address in BDD.
     * @param address String
     * @return List<PersonEntity>
     */
    public List<PersonEntity> getPersonsByAddress(
            final String address) {
        List<PersonEntity> peList =
                personRepository.findPersonEntityByAddress(address);
        if (peList.isEmpty()) {
            throw new NoSuchElementException(
                    "No persons found with address : " + address);
        }
        return peList;
    }

    /**
     * Get Persons by a address List in BDD.
     * @param addresses String
     * @return List<PersonEntity>
     */
    public List<PersonEntity> getPersonsByAddresses(
            final List<String> addresses) {
        List<PersonEntity> peList =
                personRepository.findAllByAddressIn(addresses);
        if (peList.isEmpty()) {
            throw  new NoSuchElementException(
                    "No persons found with address : " + addresses);
        }
        return peList;
    }

    /**
     * Update Person information.
     * @param personNewInfos PersonEntity
     * @return saving PersonEntity updated
     */
    public PersonEntity upDatePersonInfo(final PersonEntity personNewInfos) {
        long id = personNewInfos.getId();
        PersonEntity pe = getPersonById(id);
        pe.setAddress(personNewInfos.getAddress());
        pe.setCity(personNewInfos.getCity());
        pe.setZip(personNewInfos.getZip());
        pe.setPhone(personNewInfos.getPhone());
        pe.setEmail(personNewInfos.getEmail());
        pe.setBirthDate(personNewInfos.getBirthDate());
        pe.setMedicalRecord(personNewInfos.getMedicalRecord());
        personRepository.save(pe);
        return pe;
    }

    public List<PersonEntity> getPersonByCity(final String city) {
        List<PersonEntity> peList =
                personRepository.findPersonsEntityByCity(city);
        if (peList.isEmpty()) {
            throw new NoSuchElementException("No person found in city " + city);
        }
        return peList;
    }

    /**
     * Delete Persons by Name.
     * @param lastName String
     * @param firstName String
     */
    public void deletePersonsByName(
            final String lastName, final String firstName) {
        List<PersonEntity> peList =  getPersonByName(lastName, firstName);
        peList.forEach(personEntity -> personRepository.delete(personEntity));
    }

    /**
     * Delete Persons by id.
     * @param id long
     */
    public void deletePersonById(final long id) {
        PersonEntity pe = getPersonById(id);
        personRepository.delete(pe);
    }

    /**
     * Calculate age person from his birthdate.
     * @param birthDate Date
     * @return long age
     */
    public long calculateAge(final Date birthDate) {
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

    public PersonEntity getPersonByMedicalRecord(
            final MedicalRecordEntity mre) {
        return personRepository.findByMedicalRecord(mre)
                .orElseThrow(() -> new NoSuchElementException(""));
    }
}
