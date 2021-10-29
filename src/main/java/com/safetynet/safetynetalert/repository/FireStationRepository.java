package com.safetynet.safetynetalert.repository;

import com.safetynet.safetynetalert.entity.FireStationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireStationRepository extends
        CrudRepository<FireStationEntity, Long> {
    /**
     * Find Stations by one Address.
     * @param address String
     * @return List FireStation
     */
    List<FireStationEntity> findFireStationEntityByAddress(String address);
}
