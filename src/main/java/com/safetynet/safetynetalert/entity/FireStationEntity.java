package com.safetynet.safetynetalert.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ElementCollection;

import java.util.List;

@Getter
@Setter
@Entity
public class FireStationEntity {
    /** Primary Key Station. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long station;
    /** Address List.*/
    @ElementCollection
    private List<String> address;
    /**
     * Setter for Station.
     * Parsing String to Long
     * @param stationInput String
     */
    public void setStation(final String stationInput) {
        this.station = Long.parseLong(stationInput);
    }

}
