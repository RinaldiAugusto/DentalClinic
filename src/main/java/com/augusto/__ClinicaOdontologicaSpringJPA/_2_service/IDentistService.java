package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistResponseDTO;
import java.util.List;

import java.util.List;
import java.util.Optional;

public interface IDentistService {
    Dentist save(Dentist dentist);
    Optional<Dentist> findById(Long id);
    void update(Dentist dentist);
    void delete(Long id) throws ResourceNotFoundException;
    List<Dentist> findAll();

    DentistResponseDTO createDentist(DentistCreateDTO dentistCreateDTO);
    DentistResponseDTO updateDentist(Long id, DentistCreateDTO dentistCreateDTO) throws ResourceNotFoundException;
    DentistResponseDTO findDentistDTOById(Long id) throws ResourceNotFoundException;
    List<DentistResponseDTO> findAllDentistDTOs();
}
