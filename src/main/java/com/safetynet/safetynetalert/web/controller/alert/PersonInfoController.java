package com.safetynet.safetynetalert.web.controller.alert;

import com.safetynet.safetynetalert.service.alert.PersonInfoService;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonFullDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    @Autowired
    private PersonInfoService personInfoService;

    @GetMapping("")
    public ResponseEntity<List<PersonFullDTO>> getPersonInfo(
            @RequestParam("firstName") final String firstName,
            @RequestParam("lastName") final String lastName) {
        log.info("GET/personInfo" + firstName + lastName);
        try {
            return ResponseEntity.ok(
                  personInfoService.getPersonInfo(firstName, lastName));
        } catch (NoSuchElementException e) {
            log.error("Get PersonInfos error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
