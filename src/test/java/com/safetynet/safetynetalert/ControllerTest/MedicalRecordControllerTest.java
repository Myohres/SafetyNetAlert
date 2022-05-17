package com.safetynet.safetynetalert.ControllerTest;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import com.safetynet.safetynetalert.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MedicalRecordService medicalRecordService;

    @Test
    public void testGetMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMedicalRecordById() throws Exception {
        mockMvc.perform(get("/medicalRecord/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMedicalRecordById_ShouldReturnNotFound() throws Exception {
        when(medicalRecordService.getMedicalRecordById(1L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/medicalRecord/id/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetMedicalRecordByPerson() throws Exception {
        mockMvc.perform(get("/medicalRecord/lastname/Boyd/firstname/John"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMedicalRecordByPerson_ShouldReturnNotFound() throws Exception {
        when(medicalRecordService.getMedicalRecordsByPerson("Boyd","John"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/medicalRecord/lastname/Boyd/firstname/John"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddMedicalRecord() throws Exception {
        mockMvc.perform(post("/medicalRecord/")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddMedicalRecord_ShouldReturnNotFound() throws Exception {
        when(medicalRecordService.addMedicalRecord(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/medicalRecord/")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testReplaceMedicalRecord() throws Exception {
        mockMvc.perform(put("/medicalRecord/1")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReplaceMedicalRecord_ShouldReturnNotFound() throws Exception {
        MedicalRecordEntity mre = new MedicalRecordEntity();
        when(medicalRecordService.replaceMedicalRecord(1L,mre))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/medicalRecord/1")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddMedication() throws Exception {
        mockMvc.perform(put("/medicalRecord/id/1/medication/medication test"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddMedication_ShouldReturnNotFound() throws Exception {
        when(medicalRecordService.addMedication(1L,"medication test"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/medicalRecord/id/1/medication/medication test"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddAllergy() throws Exception {
        mockMvc.perform(put("/medicalRecord/id/1/allergy/test allergy"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddAllergy_ShouldReturnNotFound() throws Exception {
        when(medicalRecordService.addAllergy(1L,"test allergy"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/medicalRecord/id/1/allergy/test allergy"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelMedicalRecordById() throws Exception {
        mockMvc.perform(delete("/medicalRecord/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelMedicalRecordById_ShouldReturnNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(medicalRecordService).delMedicalRecordsById(1L);
        mockMvc.perform(delete("/medicalRecord/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelMedicalRecordByName() throws Exception {
        mockMvc.perform(delete("/medicalRecord/lastname/Boyd/firstname/John"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelMedicalRecordByName_ShouldReturnNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(medicalRecordService)
                .delMedicalRecordsByPerson("Boyd","John");
        mockMvc.perform(delete("/medicalRecord/lastname/Boyd/firstname/John"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelOneMedication() throws Exception {
        mockMvc.perform(delete("/medicalRecord/id/1/medication/hydrapermazol:100mg"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelOneMedication_ShouldReturnNotFound() throws Exception {
        when(medicalRecordService.delOneMedication(1L,"hydrapermazol:100mg"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/medicalRecord/id/1/medication/hydrapermazol:100mg"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelOneAllergy() throws Exception {
        mockMvc.perform(delete("/medicalRecord/id/1/allergy/nillacilan"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelOneAllergy_ShouldReturnNotFound() throws Exception {
        when(medicalRecordService.delOneAllergy(1L,"nillacilan"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/medicalRecord/id/1/allergy/nillacilan"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testClearMedications() throws Exception {
        mockMvc.perform(delete("/medicalRecord/id/1/medications"))
                .andExpect(status().isOk());
    }

    @Test
    public void testClearMedications_ShouldReturnNotFound() throws Exception {
        when(medicalRecordService.clearMedications(1L))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/medicalRecord/id/1/medications"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testClearAllergies() throws Exception {
        mockMvc.perform(delete("/medicalRecord/id/1/allergies"))
                .andExpect(status().isOk());
    }

    @Test
    public void testClearAllergie_ShouldReturnNotFounds() throws Exception {
        when(medicalRecordService.clearAllergies(1L))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/medicalRecord/id/1/allergies"))
                .andExpect(status().isNotFound());
    }
}