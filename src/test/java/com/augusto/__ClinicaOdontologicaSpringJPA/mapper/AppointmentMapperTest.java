package com.augusto.__ClinicaOdontologicaSpringJPA.mapper;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Appointment;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IPatientService;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IDentistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentMapperTest {

    @Mock
    private IPatientService patientService;

    @Mock
    private IDentistService dentistService;

    @InjectMocks
    private AppointmentMapper appointmentMapper;

    @Test
    void testToResponseDTO_FromAppointment() {
        // Given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Juan");
        patient.setLastName("Perez");

        Dentist dentist = new Dentist();
        dentist.setId(1L);
        dentist.setName("Dr. Garcia");
        dentist.setLastName("Lopez");

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDate(LocalDate.of(2024, 1, 15));
        appointment.setPatient(patient);
        appointment.setDentist(dentist);

        // When
        AppointmentResponseDTO dto = appointmentMapper.toResponseDTO(appointment);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(LocalDate.of(2024, 1, 15), dto.getDate());
        assertEquals(1L, dto.getPatientId());
        assertEquals(1L, dto.getDentistId());
    }

    @Test
    void testToResponseDTO_FromAppointment_NullInput() {
        // When & Then
        assertNull(appointmentMapper.toResponseDTO((Appointment) null));
    }

    @Test
    void testToResponseDTO_FromAppointment_NullPatientAndDentist() {
        // Given
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDate(LocalDate.of(2024, 1, 15));
        // patient and dentist are null

        // When
        AppointmentResponseDTO dto = appointmentMapper.toResponseDTO(appointment);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertNull(dto.getPatientId());
        assertNull(dto.getDentistId());
    }

    @Test
    void testToResponseDTO_FromAppointmentDTO() {
        // Given
        AppointmentDTO oldDTO = new AppointmentDTO();
        oldDTO.setId(1L);
        oldDTO.setPatient_id(1L);
        oldDTO.setDentist_id(1L);
        oldDTO.setDate("2024-01-15");

        // When
        AppointmentResponseDTO newDTO = appointmentMapper.toResponseDTO(oldDTO);

        // Then
        assertNotNull(newDTO);
        assertEquals(1L, newDTO.getId());
        assertEquals(LocalDate.of(2024, 1, 15), newDTO.getDate());
        assertEquals(1L, newDTO.getPatientId());
        assertEquals(1L, newDTO.getDentistId());
    }

    @Test
    void testToResponseDTO_FromAppointmentDTO_NullInput() {
        // When & Then
        assertNull(appointmentMapper.toResponseDTO((AppointmentDTO) null));
    }

    @Test
    void testToOldDTO() {
        // Given
        AppointmentResponseDTO newDTO = new AppointmentResponseDTO();
        newDTO.setId(1L);
        newDTO.setPatientId(1L);
        newDTO.setDentistId(1L);
        newDTO.setDate(LocalDate.of(2024, 1, 15));

        // When
        AppointmentDTO oldDTO = appointmentMapper.toOldDTO(newDTO);

        // Then
        assertNotNull(oldDTO);
        assertEquals(1L, oldDTO.getId());
        assertEquals("2024-01-15", oldDTO.getDate());
        assertEquals(1L, oldDTO.getPatient_id());
        assertEquals(1L, oldDTO.getDentist_id());
    }

    @Test
    void testToOldDTO_NullInput() {
        // When & Then
        assertNull(appointmentMapper.toOldDTO(null));
    }

    @Test
    void testToEntity() {
        // Given
        AppointmentCreateDTO createDTO = new AppointmentCreateDTO();
        createDTO.setDate(LocalDate.of(2024, 1, 15));
        createDTO.setPatientId(1L);
        createDTO.setDentistId(1L);

        Patient patient = new Patient();
        patient.setId(1L);
        Dentist dentist = new Dentist();
        dentist.setId(1L);

        when(patientService.findById(1L)).thenReturn(Optional.of(patient));
        when(dentistService.findById(1L)).thenReturn(Optional.of(dentist));

        // When
        Appointment appointment = appointmentMapper.toEntity(createDTO);

        // Then
        assertNotNull(appointment);
        assertEquals(LocalDate.of(2024, 1, 15), appointment.getDate());
        assertEquals(patient, appointment.getPatient());
        assertEquals(dentist, appointment.getDentist());

        verify(patientService, times(1)).findById(1L);
        verify(dentistService, times(1)).findById(1L);
    }

    @Test
    void testToEntity_NullInput() {
        // When & Then
        assertNull(appointmentMapper.toEntity(null));
    }

    @Test
    void testToEntity_NullPatientAndDentistIds() {
        // Given
        AppointmentCreateDTO createDTO = new AppointmentCreateDTO();
        createDTO.setDate(LocalDate.of(2024, 1, 15));
        // patientId and dentistId are null

        // When
        Appointment appointment = appointmentMapper.toEntity(createDTO);

        // Then
        assertNotNull(appointment);
        assertEquals(LocalDate.of(2024, 1, 15), appointment.getDate());
        assertNull(appointment.getPatient());
        assertNull(appointment.getDentist());

        verify(patientService, never()).findById(anyLong());
        verify(dentistService, never()).findById(anyLong());
    }
}