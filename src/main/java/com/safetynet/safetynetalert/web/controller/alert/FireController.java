package com.safetynet.safetynetalert.web.controller.alert;

import com.safetynet.safetynetalert.service.alert.FireService;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsWhenFireDTO;
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
@RequestMapping("/fire")
public class FireController {

    @Autowired
    private FireService fireService;

    @GetMapping("")
    public ResponseEntity<PersonsWhenFireDTO> getPersonFireByAddress(
            @RequestParam("address") final String address) {
        log.info("GET/fire/" + address);
        try {
            return ResponseEntity.ok(
                    fireService.getPersonsWhenFireByAddress(address));
        } catch (NoSuchElementException e) {
            log.error("Get Person by Address when Fire error : "
                    + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
