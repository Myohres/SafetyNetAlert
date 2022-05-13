package com.safetynet.safetynetalert.ControllerTest.alert;

import com.safetynet.safetynetalert.service.alert.CommunityEmailService;
import com.safetynet.safetynetalert.service.alert.FireService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireService fireService;

    @Test
    public void testGetPersonFireByAddress() throws Exception{
        mockMvc.perform(get("/fire?address=1509 Culver St "))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonFireByAddress_ShouldReturnNotFound() throws Exception{
        when(fireService.getPersonsWhenFireByAddress("1509 Culver St"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andExpect(status().isNotFound());
    }
}