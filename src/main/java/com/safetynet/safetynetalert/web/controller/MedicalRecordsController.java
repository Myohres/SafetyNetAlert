package com.safetynet.safetynetalert.web.controller;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import com.safetynet.safetynetalert.service.MedicalRecordService;
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
@RequestMapping("/medical_records")
public class MedicalRecordsController {
    /**
     * Medical record service layer.
     */
    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Get all medicalRecord.
     * @return a list of all Medical Records
     */
    @GetMapping("")
    public Iterable<MedicalRecordEntity> getMedicalRecords() {
        log.info("GET");
        return medicalRecordService.getMedicalRecords();
    }

    /**
     * Get a medicalRecord by his id.
     * @param id medical record number
     * @return MedicalRecord with id param
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<MedicalRecordEntity> getMedicalRecordById(
            @PathVariable("id") final long id) {
        log.info("GET/id/ " + id);
        try {
            return ResponseEntity.ok(
                    medicalRecordService.getMedicalRecordById(id));
        } catch (NoSuchElementException e) {
            log.error("GetMedicalRecordById error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a medicalRecord by person Name.
     * @param lastName person lastname
     * @param firstName person firstname
     * @return list of medical record corresponding to person name
     */
   @GetMapping("/lastname/{lastName}/firstname/{firstName}")
    public ResponseEntity<List<MedicalRecordEntity>> getMedicalRecordByPerson(
            @PathVariable("lastName") final String lastName,
            @PathVariable("firstName") final String firstName) {
       log.info("GET/lastname/" + lastName + "/firstname/" + firstName);
       try {
           return ResponseEntity.ok(medicalRecordService
                   .getMedicalRecordsByPerson(lastName, firstName));
       } catch (NoSuchElementException e) {
           log.error("GetMedicalRecordByPerson error : " + e.getMessage());
           return  ResponseEntity.notFound().build();
       }
    }

    /**
     * Add a medical Record.
     * @param mre medical record to add
     * @return the new medical record
     */
    @PostMapping("")
    public ResponseEntity<MedicalRecordEntity> addMedicalRecord(
            @RequestBody final MedicalRecordEntity mre) {
        log.info("POST/");
        try {
            return ResponseEntity.ok(
                    medicalRecordService.addMedicalRecord(mre));
        } catch (NoSuchElementException e) {
            log.error("add Medical Record error : No medical Record to add");
            return ResponseEntity.noContent().build();
        }

    }

    /**
     * Add a medication to a medicalRecord.
     * @param id number of medical record to update
     * @param medication medication to add
     * @return medical record updated with new medication
     */
    @PutMapping("/id/{id}/medication/{medication}")
    public ResponseEntity<MedicalRecordEntity> addMedication(
            @PathVariable("id") final long id,
            @PathVariable("medication") final String medication) {
        log.info("PUT/id/" + id + "/medication/" + medication);
        try {
            return ResponseEntity.ok(
                    medicalRecordService.addMedication(id, medication));
        } catch (NoSuchElementException e) {
            log.error("Add medication error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Replace a medicalRecord by an other.
     * @param id number of medical record to update
     * @param mre medical record to injected into medical record id param
     * @return  medicalRecord updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordEntity> replaceMedicalRecord(
            @PathVariable("id") final long id,
            @RequestBody final MedicalRecordEntity mre) {
        log.info("PUT/" + id);
        try {
            return ResponseEntity.ok(
                    medicalRecordService.replaceMedicalRecord(id, mre));
        } catch (NoSuchElementException e) {
            log.error("Replace medication error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add an allergy to a medicalRecord.
     * @param id number of medical record to update
     * @param allergy allergy to add
     * @return medicalRecord with new allergy added
     */
    @PutMapping("/id/{id}/allergy/{allergy}")
    public ResponseEntity<MedicalRecordEntity> addAllergy(
            @PathVariable("id") final long id,
            @PathVariable("allergy") final String allergy) {
        log.info("GET/id/" + id + "/allergy/" + allergy);
        try {
            return ResponseEntity.ok(
                    medicalRecordService.addAllergy(id, allergy));
        } catch (NoSuchElementException e) {
            log.error("Add allergy error : " + e.getMessage());
        }
            return ResponseEntity.notFound().build();
    }

    /**
     * Delete a medicalRecord by his id.
     * @param id number of medical record to delete
     * @return delete medical record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delMedicalRecordById(
            @PathVariable ("id") final long id) {
        log.info("DELETE/" + id);
        try {
            medicalRecordService.delMedicalRecordsById(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("Delete MedicalRecord by id error :" + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete medical record by person name.
     * @param lastName person lastname
     * @param firstName person firstname
     * @return delete medical record
     */
    @DeleteMapping("/lastname/{lastName}/firstname/{firstName}")
    public ResponseEntity<?> delMedicalRecordByPerson(
            @PathVariable("lastName") final String lastName,
            @PathVariable("firstName") final String firstName) {
        log.info("DELETE/lastname/" + lastName + "/firstname/" + firstName);
        try {
            medicalRecordService.delMedicalRecordsByPerson(lastName, firstName);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("Delete medicalRecord by person error : "
                    + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a medication from a medicalRecord.
     * @param id number of medical record to update
     * @param medication medication to delete
     * @return medical record updated
     */
    @DeleteMapping("/id/{id}/medication/{medication}")
    public ResponseEntity<MedicalRecordEntity> delOneMedication(
            @PathVariable("id") final long id,
            @PathVariable("medication") final String medication) {
        log.info("DELETE/id/" + id + "/medication/" + medication);
        try {
            return ResponseEntity.ok(
                    medicalRecordService.delOneMedication(id, medication));
        } catch (NoSuchElementException e) {
            log.error("Delete one medication error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete an allergy from a medicalRecord.
     * @param id number of medical record to update
     * @param allergy allergy to delete
     * @return medical record updated
     */
    @DeleteMapping("/id/{id}/allergy/{allergy}")
    public ResponseEntity<MedicalRecordEntity> delOneAllergy(
            @PathVariable("id") final long id,
            @PathVariable("allergy") final String allergy) {
        log.info("DELETE/id/" + id + "/allergy/" + allergy);
        try {
            return ResponseEntity.ok(
                    medicalRecordService.delOneAllergy(id, allergy));
        } catch (NoSuchElementException e) {
            log.error("Delete one allergy error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete all medications from a MedicalRecord.
     * @param id number of medical record to update
     * @return medical record updated without all medications
     */
    @DeleteMapping("/id/{id}/medications")
    public ResponseEntity<MedicalRecordEntity> clearMedications(
            @PathVariable("id") final long id) {
        log.info("DELETE/id/" + id + "/medications");
        try {
            return ResponseEntity.ok(
                    medicalRecordService.clearMedications(id));
        } catch (NoSuchElementException e) {
            log.error("Clear medications error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete all allergies from a MedicalRecord.
     * @param id number of medical record to update
     * @return medical record updated without all medications
     */
    @DeleteMapping("/id/{id}/allergies")
    public ResponseEntity<MedicalRecordEntity> clearAllergies(
            @PathVariable("id") final long id) {
        log.info("DELETE/id/" + id + "/allergies");
        try {
            return ResponseEntity.ok(medicalRecordService.clearAllergies(id));
        } catch (NoSuchElementException e) {
            log.error("Clear allergies error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
