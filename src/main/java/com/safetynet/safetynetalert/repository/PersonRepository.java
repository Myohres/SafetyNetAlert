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
     * @param address String
     * @return List PersonEntity
     */
    List<PersonEntity> findPersonEntityByAddress(String address);

    /**
     * Find Persons by a addresses list.
     * @param addresses list String
     * @return List PersonEntity
     */
    List<PersonEntity> findAllByAddressIn(List<String> addresses);

    /**
     * Find Persons by Name.
     * @param firstName String
     * @param lastName String
     * @return List PersonEntity
     */
  List<PersonEntity> findPersonEntityByFirstNameAndLastName(
            String firstName, String lastName);

  List<PersonEntity> findPersonsEntityByCity(String city);

  Optional<PersonEntity> findByMedicalRecord(
          MedicalRecordEntity medicalRecordEntity);
}
