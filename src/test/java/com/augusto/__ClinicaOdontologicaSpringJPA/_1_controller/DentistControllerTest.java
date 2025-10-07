package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IDentistService;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DentistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDentistService dentistService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllDentists() throws Exception {
        // Given
        DentistResponseDTO dentist = new DentistResponseDTO();
        dentist.setId(1L);
        dentist.setName("Dr. John");
        dentist.setLastName("Doe");

        List<DentistResponseDTO> dentists = Arrays.asList(dentist);
        when(dentistService.findAllDentistDTOs()).thenReturn(dentists);

        // When & Then
        mockMvc.perform(get("/dentist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Dr. John"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetDentistById() throws Exception {
        // Given
        DentistResponseDTO dentist = new DentistResponseDTO();
        dentist.setId(1L);
        dentist.setName("Dr. John");
        dentist.setLastName("Doe");

        when(dentistService.findDentistDTOById(anyLong())).thenReturn(dentist);

        // When & Then
        mockMvc.perform(get("/dentist/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Dr. John"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateDentist() throws Exception {
        // Given
        DentistCreateDTO createDTO = new DentistCreateDTO();
        createDTO.setName("Dr. John");
        createDTO.setLastName("Doe");
        createDTO.setRegistration(12345);

        DentistResponseDTO responseDTO = new DentistResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Dr. John");
        responseDTO.setLastName("Doe");

        when(dentistService.createDentist(any(DentistCreateDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(post("/dentist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Dr. John"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteDentist() throws Exception {
        // Given - Para métodos void, no necesitamos when().thenReturn()

        // When & Then
        mockMvc.perform(delete("/dentist/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}