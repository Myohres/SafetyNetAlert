package com.safetynet.safetynetalert.web.controller;

import com.safetynet.safetynetalert.entity.FireStationEntity;
import com.safetynet.safetynetalert.service.FireStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/fire_stations")
public class FireStationsController {
    /**
     * FireStation service layer.
     */
    @Autowired
    private FireStationService fireStationService;

    /**
     * Get all FireStations.
     * @return a list with all fireStation
     */
    @GetMapping("")
    public Iterable<FireStationEntity> getFireStations() {
        log.info("GET");
        return fireStationService.getFireStations();
    }

    /**
     * Get fireStation by his id.
     * @param id fireStation number
     * @return a FireStation
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<FireStationEntity> getFireStationById(
            @PathVariable("id") final long id) {
        log.info("GET/id/" + id);
        try {
            return ResponseEntity.ok(fireStationService.getFireStationById(id));
        } catch (NoSuchElementException e) {
            log.error("GetFireStation error :" + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get fireStations by address.
     * @param address street name and number
     * @return a list of FireStation who served in address
     */
    @GetMapping("/address")
    public ResponseEntity<List<FireStationEntity>> getStationByAddress(
            @RequestParam("address") final String address) {
        log.info("GET/address " + address);
        try {
            return ResponseEntity.ok(
                    fireStationService.getFireStationByAddress(address));
        } catch (NoSuchElementException e) {
            log.error("GetStationByAddress error :" + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a fireStation.
     * @param fireStationEntity Station number and address list served
     * @return a new fireStation
     */
    @PostMapping("/station")
    public ResponseEntity<FireStationEntity> addFireStation(
            @RequestBody final FireStationEntity fireStationEntity) {
        log.info("POST/station");
        try {
            return ResponseEntity.ok(
                    fireStationService.addFireStation(fireStationEntity));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add an address to a fireStation.
     * @param address Street name and number
     * @param id station number
     * @return FireStation updated with new address
     */
    @PostMapping("/id/{id}/address")
    public ResponseEntity<FireStationEntity> addAddress(
            @RequestParam("address") final String address,
            @PathVariable("id") final long id) {
        log.info("POST/id/" + id + "/address " + address);
        try {
            return ResponseEntity.ok(
                    fireStationService.addAddress(address, id));
        } catch (NoSuchElementException e) {
            log.error("Add address error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Move an address from a fireStation to an other fireStation.
     * @param oldId station to take address
     * @param newId station who received address
     * @param address street name and number
     * @return FireStation updated with new address
     */
    @PutMapping("/oldId/{oldId}/newId/{newId}/address")
    public ResponseEntity<FireStationEntity> moveAddressStation(
            @PathVariable("oldId") final long oldId,
            @PathVariable("newId") final long newId,
            @RequestParam("address") final String address) {
        log.info("PUT/oldId/" + oldId + "/newId/"
                + newId + "/address/" + address);
        try {
            return ResponseEntity.ok(
                    fireStationService.moveAddressStation(
                            oldId, address, newId));
        } catch (NoSuchElementException e) {
            log.error("MoveAddressStation error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Change the number of a FireStation.
     * @param oldId Station to change
     * @param newId new station number
     * @return fireStation updated
     */
    @PutMapping("/oldId/{oldId}/newId/{newId}")
    public ResponseEntity<FireStationEntity> changeStationNumber(
            @PathVariable("oldId") final long oldId,
            @PathVariable("newId") final String newId) {
        log.info("PUT/oldId/" + oldId + "/newId/" + newId);
        try {
            return ResponseEntity.ok(
                    fireStationService.changeStationNumber(oldId, newId));
        } catch (NoSuchElementException e) {
            log.error("changeStationNumber error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a fireStation.
     * @param station station number to delete
     * @return delete station
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delFireStation(
            @PathVariable("id") final long station) {
        try {
            log.info("DELETE/id/" + station);
            fireStationService.deleteFireStation(station);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("Delete fireStation error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete an address from a fireStation.
     * @param address street name and number
     * @param id station number to delete address
     * @return fireStation updated
     */
    @DeleteMapping("/id/{id}/address")
    public ResponseEntity<FireStationEntity> deleteAddressStation(
            @RequestParam("address") final String address,
            @PathVariable("id") final long id) {
        try {
            log.info("DELETE/id/" + id + "/address " + address);
            return ResponseEntity.ok(
                    fireStationService.deleteAddressStation(id, address));
        } catch (NoSuchElementException e) {
            log.error("Delete Address error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete all addresses from a fireStation.
     * @param id station number to delete addresses
     * @return fireStation updated
     */
    @DeleteMapping("/id/{id}/addresses")
    public ResponseEntity<FireStationEntity> deleteAllAddressesStation(
            @PathVariable("id") final long id) {
        log.info("DELETE/id/" + id + "/addresses");
        try {
            return ResponseEntity.ok(
                    fireStationService.deleteAllAddressesStation(id));
        } catch (NoSuchElementException e) {
            log.error("Delete All address : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
