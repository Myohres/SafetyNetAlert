package com.safetynet.safetynetalert.ControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void testGetPersons() throws Exception {
        when(personService.getPersons()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersons_shouldReturnNotFound() throws Exception {
        when(personService.getPersons()).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/persons"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPersonsById_shouldReturnOk() throws Exception {
        when(personService.getPersonById(any())).thenReturn(new PersonEntity());
        mockMvc.perform(get("/persons/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonsById_shouldReturnNotFound() throws Exception {
        when(personService.getPersonById(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/persons/id/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPersonsByName() throws Exception {
        mockMvc.perform(get("/persons/lastname/Boyd/firstname/John"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonsByName_shouldReturnNotFound() throws Exception {
        when(personService.getPersonByName(any(),any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/persons/lastname/Boyd/firstname/Joh"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPersonByAddress() throws Exception {
        mockMvc.perform(get("/persons/address/25 rue auhe"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonByAddress_NotPersonFound() throws Exception{
       when(personService.getPersonsByAddress(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/persons/address/25 rue auhe"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddPerson() throws Exception {
        mockMvc.perform(post("/persons").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddPersonNotFound() throws Exception {
        when(personService.addPerson(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/persons").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

   @Test
    public void testUpdatePerson() throws Exception {
       when(personService.upDatePersonInfo(any())).thenReturn(new PersonEntity());
       mockMvc.perform(put("/persons").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePerson_shouldReturnNotFound() throws Exception {
        when(personService.upDatePersonInfo(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/persons").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePersonByName() throws Exception {
        when(personService.getPersonByName(any(),any())).thenReturn(new ArrayList<>());
        mockMvc.perform(delete("/persons/lastname/Boyd/firstname/John"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePersonByNameNoPersonFound() throws Exception {
        doThrow(new NoSuchElementException()).when(personService).deletePersonsByName("Boyd","John");
        mockMvc.perform(delete("/persons/lastname/Boyd/firstname/John"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePersonByIdTest() throws Exception {
        mockMvc.perform(delete("/persons/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePersonByIdTestPersonNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(personService).deletePersonById(1L);
        mockMvc.perform(delete("/persons/id/1"))
                .andExpect(status().isNotFound());

    }
}

