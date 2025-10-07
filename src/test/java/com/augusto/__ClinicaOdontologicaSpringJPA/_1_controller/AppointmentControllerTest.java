package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IAppointmentService;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentResponseDTO;
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
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllAppointments() throws Exception {
        // Given
        AppointmentResponseDTO appointment = new AppointmentResponseDTO();
        appointment.setId(1L);
        appointment.setDate(LocalDate.now());

        List<AppointmentResponseDTO> appointments = Arrays.asList(appointment);
        when(appointmentService.findAllAppointmentResponses()).thenReturn(appointments);

        // When & Then
        mockMvc.perform(get("/appointment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAppointmentById() throws Exception {
        // Given
        AppointmentResponseDTO appointment = new AppointmentResponseDTO();
        appointment.setId(1L);
        appointment.setDate(LocalDate.now());

        when(appointmentService.findAppointmentResponseById(anyLong())).thenReturn(appointment);

        // When & Then
        mockMvc.perform(get("/appointment/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateAppointment() throws Exception {
        // Given
        AppointmentCreateDTO createDTO = new AppointmentCreateDTO();
        createDTO.setDate(LocalDate.now());
        createDTO.setPatientId(1L);
        createDTO.setDentistId(1L);

        AppointmentResponseDTO responseDTO = new AppointmentResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setDate(LocalDate.now());

        when(appointmentService.createAppointment(any(AppointmentCreateDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteAppointment() throws Exception {
        // Given - Para métodos void, no necesitamos when().thenReturn()
        // Simplemente no ponemos nada o usamos doNothing() si es necesario

        // When & Then
        mockMvc.perform(delete("/appointment/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}