package com.safetynet.safetynetalert.ServiceTest;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.repository.MedicalRecordRepository;
import com.safetynet.safetynetalert.repository.PersonRepository;
import com.safetynet.safetynetalert.service.MedicalRecordService;
import com.safetynet.safetynetalert.service.PersonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    @Mock
    MedicalRecordRepository medicalRecordRepository;

    @Mock
    PersonService personService;

    @InjectMocks
    MedicalRecordService medicalRecordService;


    PersonEntity pe1 = new PersonEntity();
    PersonEntity pe2 = new PersonEntity();
    MedicalRecordEntity mre1 = new MedicalRecordEntity();

    @BeforeAll
    static void beforeAll() {

    }

    @Test
    void getMedicalRecords() {
        medicalRecordService.getMedicalRecords();
        verify(medicalRecordRepository,times(1)).findAll();
    }

    @Test
    void getMedicalRecordById() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(new MedicalRecordEntity()));
        medicalRecordService.getMedicalRecordById(1L);
        verify(medicalRecordRepository,times(1)).findById(1L);

    }

    @Test
    void getMedicalRecordByIdNoFoundId() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> medicalRecordService.getMedicalRecordById(1L));
    }

    @Test
    void getMedicalRecordsByPerson() {
        List<PersonEntity> peList = new ArrayList<>();
        MedicalRecordEntity mr = new MedicalRecordEntity();
        mr.setId(25L);
        mr.setMedications(new ArrayList<>());
        mr.setAllergies(new ArrayList<>());
        pe1.setMedicalRecord(mr);
        peList.add(pe1);
        when(personService.getPersonByName(any(), any())).thenReturn(peList);

        List<MedicalRecordEntity> mrList = medicalRecordService.getMedicalRecordsByPerson("","");

        assertEquals(mr, mrList.get(0));
    }

    @Test
    void getMedications() {
        MedicalRecordEntity newMre = new MedicalRecordEntity();
        List<String> medications = new ArrayList<>();
        medications.add("medication1");
        newMre.setMedications(medications);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(newMre));
        List<String> medicationList = medicalRecordService.getMedications(1L);
        assertEquals(1,medicationList.size());
    }

    @Test
    void getMedicationsWithNoMedicalRecordFound() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.empty());
        medicalRecordService.getMedications(1L);
        assertThrows(NoSuchElementException.class,()-> medicalRecordService.getMedicalRecordById(1L));
    }

    @Test
    void getMedicationsWithNoMedicationFound() {
        MedicalRecordEntity newMre = new MedicalRecordEntity();
        List<String> medications = new ArrayList<>();
        newMre.setMedications(medications);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(newMre));
        List<String> medicationList = medicalRecordService.getMedications(1L);
        assertTrue(medicationList.isEmpty());
    }

    @Test
    void getAllergies() {
        MedicalRecordEntity newMre = new MedicalRecordEntity();
        List<String> allergy = new ArrayList<>();
        allergy.add("allergy1");
        newMre.setAllergies(allergy);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(newMre));
        List<String> allergiesList = medicalRecordService.getAllergies(1L);
        assertEquals(1,allergiesList.size());
    }

    @Test
    void getAllergiesWithNoMedicalRecordFound() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.empty());
        medicalRecordService.getAllergies(1L);
        assertThrows(NoSuchElementException.class,()-> medicalRecordService.getMedicalRecordById(1L));
    }

    @Test
    void getAllergiesWithNoMedicationFound() {
        MedicalRecordEntity newMre = new MedicalRecordEntity();
        List<String> allergies = new ArrayList<>();
        newMre.setAllergies(allergies);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(newMre));
        List<String> allergiesList = medicalRecordService.getAllergies(1L);
        assertTrue(allergiesList.isEmpty());
    }

    @Test
    void addMedicalRecord() {
        MedicalRecordEntity mre = new MedicalRecordEntity();
        when(medicalRecordRepository.save(any())).thenReturn(mre1);
        medicalRecordService.addMedicalRecord(mre);
        verify(medicalRecordRepository,times(1)).save(any());
    }

    @Test
    void addMedication() {
        MedicalRecordEntity newMre = new MedicalRecordEntity();
        List<String> medication = new ArrayList<>();
        newMre.setMedications(medication);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(newMre));
        medicalRecordService.addMedication(1L, "medication1");
        assertEquals("medication1", newMre.getMedications().get(0));
        verify(medicalRecordRepository, times(1)).save(any());
    }

    @Test
    void addMedicationWithAlreadySameMedication() {
        MedicalRecordEntity newMre = new MedicalRecordEntity();
        List<String> medications = new ArrayList<>();
        medications.add("medication1");
        newMre.setMedications(medications);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(newMre));
        MedicalRecordEntity mre = medicalRecordService.addMedication(1L, "medication1");
        assertEquals(1, mre.getMedications().size());
    }

    @Test
    void addAllergy() {
        MedicalRecordEntity newMre = new MedicalRecordEntity();
        List<String> allergy = new ArrayList<>();
        newMre.setAllergies(allergy);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(newMre));
        medicalRecordService.addAllergy(1L, "allergy1");
        assertEquals("allergy1", newMre.getAllergies().get(0));
        verify(medicalRecordRepository, times(1)).save(any());
    }

    @Test
    void addAllergyWithAlreadySameAllergy() {
        MedicalRecordEntity newMre = new MedicalRecordEntity();
        List<String> allergy = new ArrayList<>();
        allergy.add("allergy1");
        newMre.setAllergies(allergy);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(newMre));
        MedicalRecordEntity mre = medicalRecordService.addAllergy(1L, "allergy1");
        assertEquals(1, mre.getAllergies().size());
    }

    @Test
    void replaceMedicalRecord() {
        MedicalRecordEntity inputMre = new MedicalRecordEntity();
        List<String> medications1 = new ArrayList<>();
        medications1.add("1medications1");
        inputMre.setMedications(medications1);
        List<String> allergies1 = new ArrayList<>();
        allergies1.add("1allergy1");
        inputMre.setAllergies(allergies1);
        MedicalRecordEntity mre = new MedicalRecordEntity();
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(mre));
        medicalRecordService.replaceMedicalRecord(1L, inputMre);
        assertEquals(1, mre.getAllergies().size());
        assertEquals(1, mre.getMedications().size());
        verify(medicalRecordRepository,times(1)).save(any());
    }

    @Test
    void changeAllergy() {
        MedicalRecordEntity newMre1 = new MedicalRecordEntity();
        List<String> allergies1 = new ArrayList<>();
        allergies1.add("1allergy1");
        newMre1.setAllergies(allergies1);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(newMre1));
        medicalRecordService.changeAllergy(1L,"1allergy1", "1newAllergy");
        assertEquals("1newAllergy", newMre1.getAllergies().get(0));
    }

    @Test
    void changeMedication() {
        MedicalRecordEntity newMre1 = new MedicalRecordEntity();
        List<String> medications1 = new ArrayList<>();
        medications1.add("1medication1");
        newMre1.setMedications(medications1);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(newMre1));
        medicalRecordService.changeMedication(1L,"1medication1", "1newMedication1");
        assertEquals("1newMedication1", newMre1.getMedications().get(0));
    }

    @Test
    void delMedicalRecordsById() {
        PersonEntity pe = new PersonEntity();
        MedicalRecordEntity mre = new MedicalRecordEntity();
        pe.setMedicalRecord(mre);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(mre));
        when(personService.upDatePersonInfo(pe)).thenReturn(pe);
        when(personService.getPersonByMedicalRecord(mre)).thenReturn(pe);

        medicalRecordService.delMedicalRecordsById(1L);

        verify(medicalRecordRepository,times(1)).delete(any());
        verify(personService,times(1)).upDatePersonInfo(pe);
    }

    @Test
    public void delMedicalRecordsByPerson() {
        List<PersonEntity> peList = new ArrayList<>();
        peList.add(pe1);
        peList.add(pe2);
        when(personService.getPersonByName(any(),any())).thenReturn(peList);
        medicalRecordService.delMedicalRecordsByPerson("","");
        verify(medicalRecordRepository,times(2)).delete(any());
    }

    @Test
    void clearMedications() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(mre1));
        medicalRecordService.clearMedications(1L);
        verify(medicalRecordRepository,times(1)).save(any());
    }

    @Test
    void delOneMedication() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(mre1));
        medicalRecordService.delOneMedication(1,"");
        verify(medicalRecordRepository,times(1)).save(any());
    }

    @Test
    public void delOneMedicationWithoutThisMedication() {
        MedicalRecordEntity mre = new MedicalRecordEntity();
        mre.setId(1L);
        List<String> medications = new ArrayList<>();
        medications.add("medication1");
        mre.setMedications(medications);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(mre));
        assertThrows(NoSuchElementException.class, () -> medicalRecordService.delOneMedication(1,"medication1"));
    }

    @Test
    void clearAllergies() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(mre1));
        medicalRecordService.clearAllergies(1L);
        verify(medicalRecordRepository,times(1)).save(any());
    }

    @Test
    void delOneAllergy() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(mre1));
        medicalRecordService.delOneAllergy(1,"");
        verify(medicalRecordRepository,times(1)).save(any());
    }

    @Test
    public void delOneAllergyWithoutThisAllergy() {
        MedicalRecordEntity mre = new MedicalRecordEntity();
        mre.setId(1L);
        List<String> allergies = new ArrayList<>();
        allergies.add("allergy1");
        mre.setAllergies(allergies);
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(mre));
        assertThrows(NoSuchElementException.class, () -> medicalRecordService.delOneAllergy(1,"allergy1"));
    }
}