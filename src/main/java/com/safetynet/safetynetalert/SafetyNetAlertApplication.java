package com.safetynet.safetynetalert;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetAlertApplication implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication.run(SafetyNetAlertApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        // Extraire les données JSON
        // Modéliser ces données
        // Insérer les données dans la BDD
    }
}
