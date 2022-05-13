package com.safetynet.safetynetalert.ControllerTest.alert;

import com.safetynet.safetynetalert.service.alert.PersonsByStationService;
import com.safetynet.safetynetalert.service.alert.PhoneAlertService;
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
class PhoneAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneAlertService phoneAlertService;

    @Test
    public void testGetPhoneByStation() throws Exception{
        mockMvc.perform(get("/phoneAlert?fireStation=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPhoneByStation_ShouldReturnNotFound() throws Exception{
        when(phoneAlertService.getPhoneByFireStation(1L))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/phoneAlert?fireStation=1"))
                .andExpect(status().isNotFound());
    }
}