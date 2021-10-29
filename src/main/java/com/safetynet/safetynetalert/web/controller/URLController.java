package com.safetynet.safetynetalert.web.controller;

import com.safetynet.safetynetalert.service.URLService;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsByFireStationAddressesDTO;
import com.safetynet.safetynetalert.web.dto.groupProfil.FamilyByAddressDTO;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsWhenFireDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNamePhoneAgeMedical;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonFullDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("")
public class URLController {

    @Autowired
    private URLService urlService;

    @GetMapping("/fireStation")
    public ResponseEntity<PersonsByFireStationAddressesDTO>
    getPersonByFireStation(
            @RequestParam("stationNumber") final long id) {
        log.info("GET/fireStation/" + id);
        try {
            return ResponseEntity.ok(urlService.getPersonByFireStation(id));
        } catch (NoSuchElementException e) {
            log.error("Get Persons by fireStation error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/childAlert")
    public ResponseEntity<FamilyByAddressDTO> getChildByAddress(
            @RequestParam("address") final String address) {
        log.info("GET/childAlert/" + address);
        try {
            return ResponseEntity.ok(urlService.getFamilyByAddress(address));
        } catch (NoSuchElementException e) {
            log.error("Get Family By address error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneByFireStation(
            @RequestParam("fireStation") final long id) {
        log.info("GET/phoneAlert/" + id);
        try {
            return ResponseEntity.ok(urlService.getPhoneByFireStation(id));
        } catch (NoSuchElementException e) {
            log.error("getPhoneByFireStation : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fire")
    public ResponseEntity<PersonsWhenFireDTO> getPersonFireByAddress(
            @RequestParam("address") final String address) {
        log.info("GET/fire/" + address);
        try {
            return ResponseEntity.ok(
                    urlService.getPersonsWhenFireByAddress(address));
        } catch (NoSuchElementException e) {
            log.error("Get Person by Address when Fire error : "
                    + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<Map<String, List<PersonNamePhoneAgeMedical>>>
    getFoyersByFireStation(
            @RequestParam("stations") final List<Long> stationsList) {
        log.info("GET/flood/stations/" + stationsList);
        try {
            return ResponseEntity.ok(
                    urlService.getFoyersByFireStations(stationsList));
        } catch (NoSuchElementException e) {
            log.error("getFoyersByFireStation error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonFullDTO>> getPersonInfo(
            @RequestParam("firstName") final String firstName,
            @RequestParam("lastName") final String lastName) {
        log.info("GET/personInfo" + firstName + lastName);
        try {
            return ResponseEntity.ok(
                    urlService.getPersonInfo(firstName, lastName));
        } catch (NoSuchElementException e) {
            log.error("Get PersonInfos error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getEmailsCity(
            @RequestParam("city") final String city) {
        log.info("GET/communityEmail/" + city);
        try {
            return ResponseEntity.ok(urlService.getEmailsCity(city));
        } catch (NoSuchElementException e) {
            log.error("Get Emails City error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
