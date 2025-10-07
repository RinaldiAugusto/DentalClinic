package com.augusto.__ClinicaOdontologicaSpringJPA.mapper;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DentistMapperTest {

    @InjectMocks
    private DentistMapper dentistMapper;

    @Test
    void testToResponseDTO() {
        // Given
        Dentist dentist = new Dentist();
        dentist.setId(1L);
        dentist.setName("Dr. John");
        dentist.setLastName("Doe");
        dentist.setRegistration(12345);

        // When
        DentistResponseDTO dto = dentistMapper.toResponseDTO(dentist);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Dr. John", dto.getName());
        assertEquals("Doe", dto.getLastName());
        assertEquals(12345, dto.getRegistration());
    }

    @Test
    void testToResponseDTO_NullInput() {
        // When & Then
        assertNull(dentistMapper.toResponseDTO(null));
    }

    @Test
    void testToEntity() {
        // Given
        DentistCreateDTO createDTO = new DentistCreateDTO();
        createDTO.setName("Dr. Jane");
        createDTO.setLastName("Smith");
        createDTO.setRegistration(67890);

        // When
        Dentist dentist = dentistMapper.toEntity(createDTO);

        // Then
        assertNotNull(dentist);
        assertEquals("Dr. Jane", dentist.getName());
        assertEquals("Smith", dentist.getLastName());
        assertEquals(67890, dentist.getRegistration());
    }

    @Test
    void testToEntity_NullInput() {
        // When & Then
        assertNull(dentistMapper.toEntity(null));
    }

    @Test
    void testUpdateEntityFromDTO() {
        // Given
        Dentist existingDentist = new Dentist();
        existingDentist.setName("Old Name");
        existingDentist.setLastName("Old LastName");
        existingDentist.setRegistration(11111);

        DentistCreateDTO updateDTO = new DentistCreateDTO();
        updateDTO.setName("New Name");
        updateDTO.setLastName("New LastName");
        updateDTO.setRegistration(22222);

        // When
        dentistMapper.updateEntityFromDTO(updateDTO, existingDentist);

        // Then
        assertEquals("New Name", existingDentist.getName());
        assertEquals("New LastName", existingDentist.getLastName());
        assertEquals(22222, existingDentist.getRegistration());
    }

    @Test
    void testUpdateEntityFromDTO_PartialUpdate() {
        // Given
        Dentist existingDentist = new Dentist();
        existingDentist.setName("Original Name");
        existingDentist.setLastName("Original LastName");
        existingDentist.setRegistration(11111);

        DentistCreateDTO updateDTO = new DentistCreateDTO();
        updateDTO.setName("New Name");
        // lastName and registration are null

        // When
        dentistMapper.updateEntityFromDTO(updateDTO, existingDentist);

        // Then
        assertEquals("New Name", existingDentist.getName());
        assertEquals("Original LastName", existingDentist.getLastName()); // unchanged
        assertEquals(11111, existingDentist.getRegistration()); // unchanged
    }

    @Test
    void testUpdateEntityFromDTO_NullInput() {
        // Given
        Dentist dentist = new Dentist();
        dentist.setName("Original Name");

        // When - null DTO
        dentistMapper.updateEntityFromDTO(null, dentist);

        // Then - should not change
        assertEquals("Original Name", dentist.getName());

        // When - null Dentist
        DentistCreateDTO dto = new DentistCreateDTO();
        dto.setName("New Name");
        dentistMapper.updateEntityFromDTO(dto, null);

        // Then - should not throw exception
    }
}