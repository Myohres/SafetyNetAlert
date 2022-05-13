package com.safetynet.safetynetalert.web.controller.alert;

import com.safetynet.safetynetalert.service.alert.ChildAlertService;
import com.safetynet.safetynetalert.web.dto.groupProfil.FamilyByAddressDTO;
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
@RequestMapping("/childAlert")
public class ChildAlertController {

    @Autowired
    private ChildAlertService childAlertService;

    @GetMapping("")
    public ResponseEntity<FamilyByAddressDTO> getChildByAddress(
            @RequestParam("address") final String address) {
        log.info("GET/childAlert/" + address);
        try {
            return ResponseEntity.ok(childAlertService.getFamilyByAddress(address));
        } catch (NoSuchElementException e) {
            log.error("Get Family By address error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
