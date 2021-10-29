package com.safetynet.safetynetalert.web.controller;

import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/persons")
public class PersonsController {

    @Autowired
    private PersonService personService;

    @GetMapping("")
    public ResponseEntity<Iterable<PersonEntity>> getPersons() {
        log.info("GET/");
        try {
            return ResponseEntity.ok(personService.getPersons());
        } catch (NoSuchElementException e) {
            log.error("GetPersons error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PersonEntity> getPerson(
            @PathVariable("id") final Long id) {
        log.info("GET/id/" + id);
        try {
            return ResponseEntity.ok(personService.getPersonById(id));
        } catch (NoSuchElementException e) {
            log.error("GetPerson error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lastname/{lastName}/firstname/{firstName}")
    public ResponseEntity<List<PersonEntity>> getPersonByName(
            @PathVariable("lastName") final String lastName,
            @PathVariable("firstName") final String firstName) {
        log.info("GET/lastname/" + lastName + "/firstname/" + firstName);
        try {
            return ResponseEntity.ok(
                    personService.getPersonByName(lastName, firstName));
        } catch (NoSuchElementException e) {
            log.error("GetPersonByName error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("address/{address}")
    public ResponseEntity<List<PersonEntity>> getPersonsByAddress(
            @PathVariable("address") final String address) {
        log.info("GET/address/" + address);
        try {
            return ResponseEntity.ok(
                    personService.getPersonsByAddress(address));
        } catch (NoSuchElementException e) {
            log.error("getPersonsByAddress error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<PersonEntity> addPerson(
            @RequestBody final PersonEntity personEntity) {
        log.info("POST/" +personEntity);
        try {
            return ResponseEntity.ok(personService.addPerson(personEntity));
        } catch (NoSuchElementException e) {
            log.error("addPerson error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("")
    public ResponseEntity<PersonEntity> updatePerson(
            @RequestBody final PersonEntity personEntity) {
        log.info("PUT/" +personEntity);
        try {
            return ResponseEntity.ok(
                    personService.upDatePersonInfo(personEntity));
        } catch (NoSuchElementException e) {
            log.error("updatePerson error :" + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/lastname/{lastName}/firstname/{firsName}")
    public ResponseEntity<?> deletePersonsByName(
            @PathVariable("lastName") final String lastName,
            @PathVariable("firsName") final String firstName) {
        log.info("DELETE/lastname/" + lastName +
                "/firstname/" + firstName);
        try {
            personService.deletePersonsByName(lastName, firstName);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("DeletePersonsByName :" + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deletePersonById(
            @PathVariable ("id") final long id) {
        log.info("DELETE/persons/id/ " + id);
        try {
            personService.deletePersonById(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("DeletePersonById " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
