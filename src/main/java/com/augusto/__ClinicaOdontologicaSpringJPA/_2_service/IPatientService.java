package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IPatientService {
    Patient save(Patient patient);
    Optional<Patient> findById(Long id);
    void update(Patient patient);
    void delete(Long id) throws ResourceNotFoundException;
    List<Patient> findAll();
    @Query("SELECT p FROM Patient p WHERE p.lastName=?1")
    Optional<Patient> findByLastName(String lastName);

    Optional<Patient> findByName (String name);

    PatientResponseDTO createPatient(PatientCreateDTO patientCreateDTO);
    PatientResponseDTO updatePatient(Long id, PatientCreateDTO patientCreateDTO) throws ResourceNotFoundException;
    PatientResponseDTO findPatientDTOById(Long id) throws ResourceNotFoundException;
    List<PatientResponseDTO> findAllPatientDTOs();
}
