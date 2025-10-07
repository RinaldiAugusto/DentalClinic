package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IDentistService;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;
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

    private DentistResponseDTO createDentistResponseDTO() {
        DentistResponseDTO dentist = new DentistResponseDTO();
        dentist.setId(1L);
        dentist.setName("Dr. John");
        dentist.setLastName("Doe");
        dentist.setRegistration(12345);
        return dentist;
    }

    private DentistCreateDTO createDentistCreateDTO() {
        DentistCreateDTO createDTO = new DentistCreateDTO();
        createDTO.setName("Dr. John");
        createDTO.setLastName("Doe");
        createDTO.setRegistration(12345);
        return createDTO;
    }

    @Test
    @WithMockUser(roles = "ADMIN") // ✅ AGREGAR ESTO
    void testGetAllDentists() throws Exception {
        // Given
        DentistResponseDTO dentist = createDentistResponseDTO();
        List<DentistResponseDTO> dentists = Arrays.asList(dentist);

        when(dentistService.findAllDentistDTOs()).thenReturn(dentists);

        // When & Then
        mockMvc.perform(get("/dentist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Dr. John"));
    }

    @Test
    @WithMockUser(roles = "ADMIN") // ✅ AGREGAR ESTO
    void testGetDentistById() throws Exception {
        // Given
        DentistResponseDTO dentist = createDentistResponseDTO();
        when(dentistService.findDentistDTOById(anyLong())).thenReturn(dentist);

        // When & Then
        mockMvc.perform(get("/dentist/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dr. John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    @WithMockUser(roles = "ADMIN") // ✅ AGREGAR ESTO
    void testGetDentistById_NotFound() throws Exception {
        // Given
        when(dentistService.findDentistDTOById(anyLong())).thenThrow(new ResourceNotFoundException("Dentist not found"));

        // When & Then
        mockMvc.perform(get("/dentist/{id}", 999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN") // ✅ AGREGAR ESTO
    void testPostDentist() throws Exception {
        // Given
        DentistCreateDTO createDTO = createDentistCreateDTO();
        DentistResponseDTO responseDTO = createDentistResponseDTO();

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
    @WithMockUser(roles = "ADMIN") // ✅ AGREGAR ESTO
    void testUpdateDentist() throws Exception {
        // Given
        DentistCreateDTO updateDTO = createDentistCreateDTO();
        DentistResponseDTO responseDTO = createDentistResponseDTO();

        when(dentistService.updateDentist(anyLong(), any(DentistCreateDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(put("/dentist/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dr. John"));
    }

    @Test
    @WithMockUser(roles = "ADMIN") // ✅ AGREGAR ESTO
    void testUpdateDentist_NotFound() throws Exception {
        // Given
        DentistCreateDTO updateDTO = createDentistCreateDTO();

        when(dentistService.updateDentist(anyLong(), any(DentistCreateDTO.class)))
                .thenThrow(new ResourceNotFoundException("Dentist not found"));

        // When & Then
        mockMvc.perform(put("/dentist/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

//    @Test
//    @WithMockUser(roles = "ADMIN") // ✅ AGREGAR ESTO
//    void testDeleteDentist() throws Exception {
//        // When & Then
//        mockMvc.perform(delete("/dentist/{id}", 1L))
//                .andExpect(status().isNoContent());
//
//        verify(dentistService, times(1)).delete(1L);
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN") // ✅ AGREGAR ESTO
//    void testDeleteDentist_NotFound() throws Exception {
//        // Given
//        doThrow(new ResourceNotFoundException("Dentist not found"))
//                .when(dentistService).delete(anyLong());
//
//        // When & Then
//        mockMvc.perform(delete("/dentist/{id}", 999L))
//                .andExpect(status().isNotFound());
//    }
}