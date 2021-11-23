package com.safetynet.safetynetalert.repository;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import com.safetynet.safetynetalert.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository
        extends JpaRepository<PersonEntity, Long> {
    /**
     * Find Persons by one address.
     * @param address street name and number
     * @return a persons list who live in address param
     */
    List<PersonEntity> findPersonEntityByAddress(String address);

    /**
     * Find Persons by a addresses list.
     * @param addresses street name and number list
     * @return a persons list who live in addresses list param
     */
    List<PersonEntity> findAllByAddressIn(List<String> addresses);

    /**
     * Find Persons by their name.
     * @param firstName firstname
     * @param lastName lastname
     * @return a persons list with name param
     */
  List<PersonEntity> findPersonEntityByFirstNameAndLastName(
            String firstName, String lastName);

    /**
     * Find Persons by city who they live.
     * @param city city name
     * @return a persons list who live in city param
     */
  List<PersonEntity> findPersonsEntityByCity(String city);

    /**
     * Find Persons by their medicalRecord.
     * @param medicalRecordEntity allergies and medications list
     * @return a persons list with medical record param
     */
  Optional<PersonEntity> findByMedicalRecord(
          MedicalRecordEntity medicalRecordEntity);
}
