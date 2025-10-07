package com.augusto.__ClinicaOdontologicaSpringJPA.mapper;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Address;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AddressDTOs.AddressCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AddressDTOs.AddressDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs.PatientCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs.PatientResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PatientMapperTest {

    @InjectMocks
    private PatientMapper patientMapper;

    @Test
    void testToResponseDTO_WithAddress() {
        // Given
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Calle Falsa");
        address.setLocation("Springfield");
        address.setProvince("Buenos Aires");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Maria");
        patient.setLastName("Lopez");
        patient.setCardIdentity("12345678");
        patient.setAdmissionOfDate(LocalDate.of(2024, 1, 10));
        patient.setAddress(address);

        // When
        PatientResponseDTO dto = patientMapper.toResponseDTO(patient);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Maria", dto.getName());
        assertEquals("Lopez", dto.getLastName());
        assertEquals("12345678", dto.getCardIdentity());
        assertEquals(LocalDate.of(2024, 1, 10), dto.getAdmissionOfDate());

        assertNotNull(dto.getAddress());
        assertEquals(1L, dto.getAddress().getId());
        assertEquals("Calle Falsa", dto.getAddress().getStreet());
        assertEquals("Springfield", dto.getAddress().getLocation());
        assertEquals("Buenos Aires", dto.getAddress().getProvince());
    }

    @Test
    void testToResponseDTO_WithoutAddress() {
        // Given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Maria");
        patient.setLastName("Lopez");
        patient.setCardIdentity("12345678");
        // address is null

        // When
        PatientResponseDTO dto = patientMapper.toResponseDTO(patient);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Maria", dto.getName());
        assertEquals("Lopez", dto.getLastName());
        assertNull(dto.getAddress());
    }

    @Test
    void testToResponseDTO_NullInput() {
        // When & Then
        assertNull(patientMapper.toResponseDTO(null));
    }

    @Test
    void testToEntity_WithAddress() {
        // Given
        // CAMBIO: Usar AddressCreateDTO en lugar de AddressDTO
        AddressCreateDTO addressCreateDTO = new AddressCreateDTO();
        addressCreateDTO.setStreet("Calle Test");
        addressCreateDTO.setLocation("Location Test");
        addressCreateDTO.setProvince("Province Test");

        PatientCreateDTO createDTO = new PatientCreateDTO();
        createDTO.setName("John");
        createDTO.setLastName("Doe");
        createDTO.setCardIdentity("87654321");
        createDTO.setAdmissionOfDate(LocalDate.of(2024, 1, 5));
        createDTO.setAddress(addressCreateDTO); // ← Ahora funciona

        // When
        Patient patient = patientMapper.toEntity(createDTO);

        // Then
        assertNotNull(patient);
        assertEquals("John", patient.getName());
        assertEquals("Doe", patient.getLastName());
        assertEquals("87654321", patient.getCardIdentity());
        assertEquals(LocalDate.of(2024, 1, 5), patient.getAdmissionOfDate());

        assertNotNull(patient.getAddress());
        assertEquals("Calle Test", patient.getAddress().getStreet());
        assertEquals("Location Test", patient.getAddress().getLocation());
        assertEquals("Province Test", patient.getAddress().getProvince());
    }

    @Test
    void testToEntity_WithoutAddress() {
        // Given
        PatientCreateDTO createDTO = new PatientCreateDTO();
        createDTO.setName("John");
        createDTO.setLastName("Doe");
        createDTO.setCardIdentity("87654321");
        // address is null

        // When
        Patient patient = patientMapper.toEntity(createDTO);

        // Then
        assertNotNull(patient);
        assertEquals("John", patient.getName());
        assertEquals("Doe", patient.getLastName());
        assertNull(patient.getAddress());
    }

    @Test
    void testToEntity_NullInput() {
        // When & Then
        assertNull(patientMapper.toEntity(null));
    }

    @Test
    void testUpdateEntityFromDTO() {
        // Given
        Patient existingPatient = new Patient();
        existingPatient.setName("Old Name");
        existingPatient.setLastName("Old LastName");
        existingPatient.setCardIdentity("11111111");
        existingPatient.setAdmissionOfDate(LocalDate.of(2023, 1, 1));

        Address existingAddress = new Address();
        existingAddress.setStreet("Old Street");
        existingAddress.setLocation("Old Location");
        existingAddress.setProvince("Old Province");
        existingPatient.setAddress(existingAddress);

        // CAMBIO: Usar AddressCreateDTO
        AddressCreateDTO newAddressCreateDTO = new AddressCreateDTO();
        newAddressCreateDTO.setStreet("New Street");
        newAddressCreateDTO.setLocation("New Location");
        newAddressCreateDTO.setProvince("New Province");

        PatientCreateDTO updateDTO = new PatientCreateDTO();
        updateDTO.setName("New Name");
        updateDTO.setLastName("New LastName");
        updateDTO.setCardIdentity("22222222");
        updateDTO.setAdmissionOfDate(LocalDate.of(2024, 1, 1));
        updateDTO.setAddress(newAddressCreateDTO); // ← Ahora funciona

        // When
        patientMapper.updateEntityFromDTO(updateDTO, existingPatient);

        // Then
        assertEquals("New Name", existingPatient.getName());
        assertEquals("New LastName", existingPatient.getLastName());
        assertEquals("22222222", existingPatient.getCardIdentity());
        assertEquals(LocalDate.of(2024, 1, 1), existingPatient.getAdmissionOfDate());

        assertNotNull(existingPatient.getAddress());
        assertEquals("New Street", existingPatient.getAddress().getStreet());
        assertEquals("New Location", existingPatient.getAddress().getLocation());
        assertEquals("New Province", existingPatient.getAddress().getProvince());
    }
    @Test
    void testUpdateEntityFromDTO_NullInput() {
        // Given
        Patient patient = new Patient();
        patient.setName("Original Name");

        // When - null DTO
        patientMapper.updateEntityFromDTO(null, patient);

        // Then - should not change
        assertEquals("Original Name", patient.getName());

        // When - null Patient
        PatientCreateDTO dto = new PatientCreateDTO();
        dto.setName("New Name");
        patientMapper.updateEntityFromDTO(dto, null);

        // Then - should not throw exception
    }
}