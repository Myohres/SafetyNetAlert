package com.safetynet.safetynetalert.ControllerTest;

import com.safetynet.safetynetalert.service.URLService;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsByFireStationAddressesDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class URLControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    URLService urlService;

    @Test
    public void testGetPersonByStation() throws Exception{
        when(urlService.getPersonByFireStation(1L))
                .thenReturn(new PersonsByFireStationAddressesDTO());
        mockMvc.perform(get("/fireStation?stationNumber=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonByStation_ShouldReturnNotFound() throws Exception{
        when(urlService.getPersonByFireStation(1L))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/fireStation?stationNumber=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetFamilyByAddress() throws Exception{
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFamilyByAddress_ShouldReturnNotFound() throws Exception{
        when(urlService.getFamilyByAddress("1509 Culver St"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPhoneByStation() throws Exception{
        mockMvc.perform(get("/phoneAlert?fireStation=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPhoneByStation_ShouldReturnNotFound() throws Exception{
        when(urlService.getPhoneByFireStation(1L))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/phoneAlert?fireStation=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPersonFireByAddress() throws Exception{
        mockMvc.perform(get("/fire?address=1509 Culver St "))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonFireByAddress_ShouldReturnNotFound() throws Exception{
        when(urlService.getPersonsWhenFireByAddress("1509 Culver St"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetFoyerByFireStation() throws Exception{
        mockMvc.perform(get("/flood/stations?stations=1,2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFoyerByFireStation_ShouldReturnNotFound() throws Exception{
        List<Long> stationList = new ArrayList<>();
        stationList.add(1L);
        stationList.add(2L);
        when(urlService.getFoyersByFireStations(stationList))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/flood/stations?stations=1,2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPersonInfo() throws Exception{
        mockMvc.perform(get("/personInfo?firstName=Jacob&lastName=Boyd"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonInfo_ShouldReturnNotFound() throws Exception{
        when(urlService.getPersonInfo("Jacob","Boyd"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/personInfo?firstName=Jacob&lastName=Boyd"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetEmailsCity() throws Exception{
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEmailsCity_ShouldReturnNotFound() throws Exception{
        when(urlService.getEmailsCity("Culver"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().isNotFound());
    }
}
