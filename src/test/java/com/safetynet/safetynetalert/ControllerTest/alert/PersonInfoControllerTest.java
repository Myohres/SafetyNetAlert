package com.safetynet.safetynetalert.ControllerTest.alert;

import com.safetynet.safetynetalert.service.alert.FloodService;
import com.safetynet.safetynetalert.service.alert.PersonInfoService;
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
class PersonInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonInfoService personInfoService;

    @Test
    public void testGetPersonInfo() throws Exception{
        mockMvc.perform(get("/personInfo?firstName=Jacob&lastName=Boyd"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonInfo_ShouldReturnNotFound() throws Exception{
        when(personInfoService.getPersonInfo("Jacob","Boyd"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/personInfo?firstName=Jacob&lastName=Boyd"))
                .andExpect(status().isNotFound());
    }
}