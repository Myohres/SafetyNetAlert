package com.safetynet.safetynetalert.ControllerTest.alert;

import com.safetynet.safetynetalert.service.alert.PersonsByStationService;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsByFireStationAddressesDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonByStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonsByStationService personsByStationService;

    @Test
    public void testGetPersonByStation() throws Exception{
        when(personsByStationService.getPersonByFireStation(1L))
                .thenReturn(new PersonsByFireStationAddressesDTO());
        mockMvc.perform(get("/fireStation?stationNumber=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonByStation_ShouldReturnNotFound() throws Exception{
        when(personsByStationService.getPersonByFireStation(1L))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/fireStation?stationNumber=1"))
                .andExpect(status().isNotFound());
    }
}