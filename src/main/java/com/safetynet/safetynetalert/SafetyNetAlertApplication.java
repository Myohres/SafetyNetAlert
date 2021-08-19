package com.safetynet.safetynetalert;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetAlertApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SafetyNetAlertApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Extraire les données JSON
        // Modéliser ces données
        // Insérer les données dans la BDD
    }
}
