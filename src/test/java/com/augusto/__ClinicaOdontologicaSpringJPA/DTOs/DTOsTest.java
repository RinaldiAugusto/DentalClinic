package com.augusto.__ClinicaOdontologicaSpringJPA.DTOs;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AddressDTOs.AddressCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AddressDTOs.AddressDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.AuthResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.LoginRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.RegisterRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs.PatientCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs.PatientResponseDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DTOsTest {

    // ===== ADDRESS DTOS =====
    @Test
    void testAddressCreateDTO() {
        AddressCreateDTO dto = new AddressCreateDTO();
        dto.setStreet("Calle Test 123");
        dto.setLocation("Springfield");
        dto.setProvince("Buenos Aires");

        assertEquals("Calle Test 123", dto.getStreet());
        assertEquals("Springfield", dto.getLocation());
        assertEquals("Buenos Aires", dto.getProvince());
    }

    @Test
    void testAddressDTO() {
        AddressDTO dto = new AddressDTO();
        dto.setId(1L);
        dto.setStreet("Calle Test 123");
        dto.setLocation("Springfield");
        dto.setProvince("Buenos Aires");

        assertEquals(1L, dto.getId());
        assertEquals("Calle Test 123", dto.getStreet());
        assertEquals("Springfield", dto.getLocation());
        assertEquals("Buenos Aires", dto.getProvince());
    }

    @Test
    void testAddressDTO_Constructor() {
        AddressDTO dto = new AddressDTO(1L, "Calle Test", "Location", "Province");

        assertEquals(1L, dto.getId());
        assertEquals("Calle Test", dto.getStreet());
        assertEquals("Location", dto.getLocation());
        assertEquals("Province", dto.getProvince());
    }

    // ===== APPOINTMENT DTOS =====
    @Test
    void testAppointmentCreateDTO() {
        AppointmentCreateDTO dto = new AppointmentCreateDTO();
        dto.setDate(LocalDate.of(2024, 1, 15));
        dto.setPatientId(1L);
        dto.setDentistId(2L);

        assertEquals(LocalDate.of(2024, 1, 15), dto.getDate());
        assertEquals(1L, dto.getPatientId());
        assertEquals(2L, dto.getDentistId());
    }

    @Test
    void testAppointmentDTO() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(1L);
        dto.setDate("2024-01-15");
        dto.setPatient_id(1L);
        dto.setDentist_id(2L);

        assertEquals(1L, dto.getId());
        assertEquals("2024-01-15", dto.getDate());
        assertEquals(1L, dto.getPatient_id());
        assertEquals(2L, dto.getDentist_id());
    }

    @Test
    void testAppointmentDTO_Constructor() {
        AppointmentDTO dto = new AppointmentDTO(1L, 2L, 3L, "2024-01-15");

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getDentist_id());
        assertEquals(3L, dto.getPatient_id());
        assertEquals("2024-01-15", dto.getDate());
    }

    @Test
    void testAppointmentResponseDTO() {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(1L);
        dto.setDate(LocalDate.of(2024, 1, 15));
        dto.setPatientId(1L);
        dto.setDentistId(2L);
        // Nota: Tus DTOs reales no tienen patientName y dentistName
        // Solo tienen los IDs

        assertEquals(1L, dto.getId());
        assertEquals(LocalDate.of(2024, 1, 15), dto.getDate());
        assertEquals(1L, dto.getPatientId());
        assertEquals(2L, dto.getDentistId());
    }

    @Test
    void testAppointmentResponseDTO_Constructor() {
        AppointmentResponseDTO dto = new AppointmentResponseDTO(1L, 2L, 3L, LocalDate.of(2024, 1, 15));

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getDentistId());
        assertEquals(3L, dto.getPatientId());
        assertEquals(LocalDate.of(2024, 1, 15), dto.getDate());
    }

    // ===== AUTH DTOS =====
    @Test
    void testLoginRequestDTO() {
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("password123");

        assertEquals("test@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
    }

    @Test
    void testRegisterRequestDTO() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setFirstName("John");
        dto.setLastName("Doe");

        assertEquals("test@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
    }

    @Test
    void testAuthResponseDTO() {
        AuthResponseDTO dto = new AuthResponseDTO();
        dto.setToken("jwt-token-here");
        dto.setEmail("test@example.com");

        assertEquals("jwt-token-here", dto.getToken());
        assertEquals("test@example.com", dto.getEmail());
        // Nota: Tus DTOs de Auth no tienen firstName y lastName en la respuesta
    }

    // ===== DENTIST DTOS =====
    @Test
    void testDentistCreateDTO() {
        DentistCreateDTO dto = new DentistCreateDTO();
        dto.setName("Dr. John");
        dto.setLastName("Doe");
        dto.setRegistration(12345);

        assertEquals("Dr. John", dto.getName());
        assertEquals("Doe", dto.getApellido());
        assertEquals(12345, dto.getRegistration());
    }

    @Test
    void testDentistResponseDTO() {
        DentistResponseDTO dto = new DentistResponseDTO();
        dto.setId(1L);
        dto.setName("Dr. John");
        dto.setLastName("Doe");
        dto.setRegistration(12345);

        assertEquals(1L, dto.getId());
        assertEquals("Dr. John", dto.getName());
        assertEquals("Doe", dto.getLastName());
        assertEquals(12345, dto.getRegistration());
    }

    @Test
    void testDentistResponseDTO_Constructor() {
        DentistResponseDTO dto = new DentistResponseDTO(1L, "Dr. John", "Doe", 12345);

        assertEquals(1L, dto.getId());
        assertEquals("Dr. John", dto.getName());
        assertEquals("Doe", dto.getLastName());
        assertEquals(12345, dto.getRegistration());
    }

    // ===== PATIENT DTOS =====
    @Test
    void testPatientCreateDTO() {
        PatientCreateDTO dto = new PatientCreateDTO();
        dto.setName("Maria");
        dto.setLastName("Lopez");
        dto.setCardIdentity("12345678");
        dto.setAdmissionOfDate(LocalDate.of(2024, 1, 10));

        // USAR AddressCreateDTO en lugar de AddressDTO
        AddressCreateDTO addressCreateDTO = new AddressCreateDTO();
        addressCreateDTO.setStreet("Calle Test");
        addressCreateDTO.setLocation("Location Test");
        addressCreateDTO.setProvince("Province Test");
        dto.setAddress(addressCreateDTO);

        assertEquals("Maria", dto.getName());
        assertEquals("Lopez", dto.getLastName());
        assertEquals("12345678", dto.getCardIdentity());
        assertEquals(LocalDate.of(2024, 1, 10), dto.getAdmissionOfDate());
        assertNotNull(dto.getAddress());
        assertEquals("Calle Test", dto.getAddress().getStreet());
    }

    @Test
    void testPatientResponseDTO() {
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(1L);
        dto.setName("Maria");
        dto.setLastName("Lopez");
        dto.setCardIdentity("12345678");
        dto.setAdmissionOfDate(LocalDate.of(2024, 1, 10));

        assertEquals(1L, dto.getId());
        assertEquals("Maria", dto.getName());
        assertEquals("Lopez", dto.getLastName());
        assertEquals("12345678", dto.getCardIdentity());
        assertEquals(LocalDate.of(2024, 1, 10), dto.getAdmissionOfDate());
    }

    @Test
    void testPatientCreateDTO_Constructor() {
        AddressCreateDTO addressCreateDTO = new AddressCreateDTO();
        addressCreateDTO.setStreet("Calle Test");
        addressCreateDTO.setLocation("Location Test");
        addressCreateDTO.setProvince("Province Test");

        PatientCreateDTO dto = new PatientCreateDTO("Maria", "Lopez", "12345678",
                LocalDate.of(2024, 1, 10), addressCreateDTO);

        assertEquals("Maria", dto.getName());
        assertEquals("Lopez", dto.getLastName());
        assertEquals("12345678", dto.getCardIdentity());
        assertEquals(LocalDate.of(2024, 1, 10), dto.getAdmissionOfDate());
        assertNotNull(dto.getAddress());
    }

    // ===== TESTS PARA DTOS QUE NO TIENES =====
    // Comentamos los tests para DTOs que no existen en tu código

    /*
    @Test
    void testNotificationRequestDTO() {
        // Este DTO no existe en tu código actual
    }

    @Test
    void testNotificationResponseDTO() {
        // Este DTO no existe en tu código actual
    }

    @Test
    void testStatsResponseDTO() {
        // Este DTO no existe en tu código actual
    }
    */

    // ===== TESTS ADICIONALES PARA COBERTURA =====
    @Test
    void testDTOsEqualsAndHashCode() {
        // Test para coverage de métodos automáticos
        AddressDTO address1 = new AddressDTO(1L, "Street", "Location", "Province");
        AddressDTO address2 = new AddressDTO(1L, "Street", "Location", "Province");

        assertEquals(address1, address2);
        assertEquals(address1.hashCode(), address2.hashCode());
    }

    @Test
    void testDTOsToString() {
        // Test para coverage del método toString
        AddressDTO address = new AddressDTO(1L, "Street", "Location", "Province");
        String toStringResult = address.toString();

        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("Street"));
    }
}