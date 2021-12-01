package com.safetynet.safetynetalert.ControllerTest;

import com.safetynet.safetynetalert.entity.FireStationEntity;
import com.safetynet.safetynetalert.repository.FireStationRepository;
import com.safetynet.safetynetalert.service.FireStationService;
import org.apache.commons.collections.list.LazyList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class FireStationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FireStationService fireStationService;

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    private void setUpPerTest(){
    }

    @Test
    public void testGetFireStations() throws Exception {
        mockMvc.perform(get("/fire_stations/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFireStationById() throws Exception {
        mockMvc.perform(get("/fire_stations/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFireStationById_shouldReturnNotFound() throws Exception {
        when(fireStationService.getFireStationById(1L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/fire_stations/id/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetStationByAddress() throws Exception {
        when(fireStationService.getFireStationByAddress(any())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/fire_stations/address?address=25 rue auge"))
        .andExpect(status().isOk());
    }

    @Test
    public void testGetStationByAddress_ShouldReturnNotFound() throws Exception {
        when(fireStationService.getFireStationByAddress(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/fire_stations/address?address=25 rue auge"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddStation() throws Exception {
        mockMvc.perform(post("/fire_stations/station")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddStation_ShouldReturnNotFound() throws Exception {
        when(fireStationService.addFireStation(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/fire_stations/station")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddAddress() throws Exception {
        mockMvc.perform(post("/fire_stations/id/1/address?address=221B Baker Street"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddAddress_ShouldReturnNotFound() throws Exception {
        when(fireStationService.addAddress("221B Baker Street",1L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/fire_stations/id/1/address?address=221B Baker Street"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testMoveAddressStation() throws Exception {
        mockMvc.perform(put("/fire_stations/oldId/1/newId/6/address?address=908 73rd St"))
                .andExpect(status().isOk());
    }

    @Test
    public void testMoveAddressStation_ShouldReturnNotFound() throws Exception {
        when(fireStationService.moveAddressStation(1L,"25 rue auge",2L))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/fire_stations/oldId/1/newId/2/address?address=25 rue auge"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testChangeStationNumber() throws Exception {
        mockMvc.perform(put("/fire_stations/oldId/4/newId/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testChangeStation_ShouldReturnNotFound() throws Exception {
        when(fireStationService.changeStationNumber(3L,"7")).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/fire_stations/oldId/3/newId/7"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFireStation() throws Exception {
        when(fireStationService.getFireStationById(2L)).thenReturn(new FireStationEntity());
        mockMvc.perform(delete("/fire_stations/id/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFireStation_ShouldReturnNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(fireStationService).deleteFireStation(1L);
        mockMvc.perform(delete("/fire_stations/id/1"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testDeleteAddressStation() throws Exception {
        when(fireStationService.getFireStationById(1L)).thenReturn(new FireStationEntity());
        mockMvc.perform(delete("/fire_stations/id/1/address?address=644 Gershwin Cir"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAddressStation_ShouldReturnNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(fireStationService)
                .deleteAddressStation(1L,"644 Gershwin Cir");
        mockMvc.perform(delete("/fire_stations/id/1/address?address=644 Gershwin Cir"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAllAddressStation() throws Exception {
        mockMvc.perform(delete("/fire_stations/id/1/addresses"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAllAddressStation_ShouldReturnNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(fireStationService).deleteAllAddressesStation(1L);
        mockMvc.perform(delete("/fire_stations/id/1/addresses"))
                .andExpect(status().isNotFound());
    }

}