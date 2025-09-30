package com.augusto.__ClinicaOdontologicaSpringJPA.mapper;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import org.springframework.stereotype.Component;

@Component
public class DentistMapper {

    // Convierte Dentist Entity → DentistResponseDTO
    public DentistResponseDTO toResponseDTO(Dentist dentist) {
        if (dentist == null) {
            return null;
        }

        return new DentistResponseDTO(
                dentist.getId(),
                dentist.getName(),
                dentist.getLastName(),
                dentist.getRegistration()
        );
    }

    // Convierte DentistCreateDTO → Dentist Entity
    public Dentist toEntity(DentistCreateDTO dentistCreateDTO) {
        if (dentistCreateDTO == null) {
            return null;
        }

        Dentist dentist = new Dentist();
        dentist.setName(dentistCreateDTO.getName());
        dentist.setLastName(dentistCreateDTO.getApellido());
        dentist.setRegistration(dentistCreateDTO.getRegistration());

        return dentist;
    }

    // Actualiza un Dentist existente con datos de DTO
    public void updateEntityFromDTO(DentistCreateDTO dentistCreateDTO, Dentist dentist) {
        if (dentistCreateDTO == null || dentist == null) {
            return;
        }

        if (dentistCreateDTO.getName() != null) {
            dentist.setName(dentistCreateDTO.getName());
        }
        if (dentistCreateDTO.getApellido() != null) {
            dentist.setLastName(dentistCreateDTO.getApellido());
        }
        if (dentistCreateDTO.getRegistration() != null) {
            dentist.setRegistration(dentistCreateDTO.getRegistration());
        }
    }
}