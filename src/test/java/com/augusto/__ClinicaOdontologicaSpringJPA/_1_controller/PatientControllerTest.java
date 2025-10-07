package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IPatientService;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AddressDTOs.AddressCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs.PatientCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs.PatientResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AddressDTOs.AddressDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllPatients() throws Exception {
        // Given
        PatientResponseDTO patient = new PatientResponseDTO();
        patient.setId(1L);
        patient.setName("John");
        patient.setLastName("Doe");
        patient.setCardIdentity("12345678");

        List<PatientResponseDTO> patients = Arrays.asList(patient);
        when(patientService.findAllPatientDTOs()).thenReturn(patients);

        // When & Then
        mockMvc.perform(get("/patient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetPatientById() throws Exception {
        // Given
        PatientResponseDTO patient = new PatientResponseDTO();
        patient.setId(1L);
        patient.setName("John");
        patient.setLastName("Doe");
        patient.setCardIdentity("12345678");

        when(patientService.findPatientDTOById(anyLong())).thenReturn(patient);

        // When & Then
        mockMvc.perform(get("/patient/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreatePatient() throws Exception {
        // Given
        PatientCreateDTO createDTO = new PatientCreateDTO();
        createDTO.setName("John");
        createDTO.setLastName("Doe");
        createDTO.setCardIdentity("12345678");
        createDTO.setAdmissionOfDate(LocalDate.of(2024, 1, 10));

        // CAMBIO: Usar AddressCreateDTO
        AddressCreateDTO addressDTO = new AddressCreateDTO();
        addressDTO.setStreet("Calle Test");
        addressDTO.setLocation("Location Test");
        addressDTO.setProvince("Province Test");
        createDTO.setAddress(addressDTO); // ← Ahora funciona

        PatientResponseDTO responseDTO = new PatientResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("John");
        responseDTO.setLastName("Doe");
        responseDTO.setCardIdentity("12345678");

        when(patientService.createPatient(any(PatientCreateDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdatePatient() throws Exception {
        // Given
        PatientCreateDTO updateDTO = new PatientCreateDTO();
        updateDTO.setName("John Updated");
        updateDTO.setLastName("Doe Updated");
        updateDTO.setCardIdentity("87654321");

        PatientResponseDTO responseDTO = new PatientResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("John Updated");
        responseDTO.setLastName("Doe Updated");
        responseDTO.setCardIdentity("87654321");

        when(patientService.updatePatient(anyLong(), any(PatientCreateDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(put("/patient/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.lastName").value("Doe Updated"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeletePatient() throws Exception {
        // Given
        // No necesitas when() para métodos void
        // Simplemente no hagas nada o usa doNothing() si es necesario

        // When & Then
        mockMvc.perform(delete("/patient/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetAllPatients_ForbiddenForUserRole() throws Exception {
        // When & Then - USER role should not have access to GET /patient
        mockMvc.perform(get("/patient"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetPatientById_NotFound() throws Exception {
        // Given
        when(patientService.findPatientDTOById(anyLong())).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/patient/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}