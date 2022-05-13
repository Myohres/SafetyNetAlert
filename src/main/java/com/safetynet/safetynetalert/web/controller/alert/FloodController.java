package com.safetynet.safetynetalert.web.controller.alert;

import com.safetynet.safetynetalert.service.alert.FloodService;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNamePhoneAgeMedical;
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
@RequestMapping("/flood")
public class FloodController {

    @Autowired
    private FloodService floodService;

    @GetMapping("/stations")
    public ResponseEntity<Map<String, List<PersonNamePhoneAgeMedical>>>
    getFoyersByFireStation(
            @RequestParam("stations") final List<Long> stationsList) {
        log.info("GET/flood/stations/" + stationsList);
        try {
            return ResponseEntity.ok(
                    floodService.getFoyersByFireStations(stationsList));
        } catch (NoSuchElementException e) {
            log.error("getFoyersByFireStation error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
