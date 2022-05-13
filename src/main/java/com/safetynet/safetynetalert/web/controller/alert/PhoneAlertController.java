package com.safetynet.safetynetalert.web.controller.alert;

import com.safetynet.safetynetalert.service.alert.PhoneAlertService;
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
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

    @Autowired
    private PhoneAlertService phoneAlertService;

    @GetMapping("")
    public ResponseEntity<List<String>> getPhoneByFireStation(
            @RequestParam("fireStation") final long id) {
        log.info("GET/phoneAlert/" + id);
        try {
            return ResponseEntity.ok(phoneAlertService.getPhoneByFireStation(id));
        } catch (NoSuchElementException e) {
            log.error("getPhoneByFireStation : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
