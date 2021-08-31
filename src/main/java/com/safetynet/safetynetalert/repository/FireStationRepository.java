package com.safetynet.safetynetalert.repository;

import com.safetynet.safetynetalert.entity.FireStationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FireStationRepository extends CrudRepository<FireStationEntity, Long> {
}
