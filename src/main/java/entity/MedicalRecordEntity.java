package entity;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecordEntity {

    private List<String> medications;
    private List<String> allergies;
}
