package com.safetynet.safetynetalert.web.controller.alert;

import com.safetynet.safetynetalert.service.alert.CommunityEmailService;
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
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    @Autowired
    private CommunityEmailService communityEmailService;

    @GetMapping("")
    public ResponseEntity<List<String>> getEmailsCity(
            @RequestParam("city") final String city) {
        log.info("GET/communityEmail/" + city);
        try {
            return ResponseEntity.ok(communityEmailService.getEmailsCity(city));
        } catch (NoSuchElementException e) {
            log.error("Get Emails City error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
