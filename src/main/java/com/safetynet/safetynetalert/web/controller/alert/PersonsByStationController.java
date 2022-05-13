package com.safetynet.safetynetalert.web.controller.alert;

import com.safetynet.safetynetalert.service.alert.PersonsByStationService;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsByFireStationAddressesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/fireStation")
public class PersonsByStationController {

    @Autowired
    private PersonsByStationService personsByStationService;

    @GetMapping("")
    public ResponseEntity<PersonsByFireStationAddressesDTO>
    getPersonByFireStation(
            @RequestParam("stationNumber") final long id) {
        log.info("GET/fireStation/" + id);
        try {
            return ResponseEntity.ok(personsByStationService.getPersonByFireStation(id));
        } catch (NoSuchElementException e) {
            log.error("Get Persons by fireStation error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
