package com.safetynet.safetynetalert.repository;

import com.safetynet.safetynetalert.entity.FireStationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireStationRepository extends
        CrudRepository<FireStationEntity, Long> {
    /**
     * Find Stations by one address.
     * @param address street name and number
     * @return a fireStation list with address param
     */
    List<FireStationEntity> findFireStationEntityByAddress(String address);
}
