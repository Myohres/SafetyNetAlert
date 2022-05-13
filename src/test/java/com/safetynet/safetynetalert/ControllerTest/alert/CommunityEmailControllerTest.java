package com.safetynet.safetynetalert.ControllerTest.alert;

import com.safetynet.safetynetalert.service.alert.ChildAlertService;
import com.safetynet.safetynetalert.service.alert.CommunityEmailService;
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
class CommunityEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommunityEmailService communityEmailService;

    @Test
    public void testGetEmailsCity() throws Exception{
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEmailsCity_ShouldReturnNotFound() throws Exception{
        when(communityEmailService.getEmailsCity("Culver"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().isNotFound());
    }
}