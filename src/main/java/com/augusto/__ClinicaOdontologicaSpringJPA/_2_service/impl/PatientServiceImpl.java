package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IPatientService;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.PatientRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import com.augusto.__ClinicaOdontologicaSpringJPA.mapper.PatientMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements IPatientService {
    private PatientRepository repository;

    @Autowired
    private PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient save(Patient patient) {
        return repository.save(patient);
    }

    public Optional<Patient> findById(Long id) {
        return repository.findById(id);
    }

    public void update(Patient patient) {
        repository.save(patient);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Patient> patientOptional = findById(id);

        if (patientOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("No se pudo eliminar el paciente con id: " + id);
        }

    }

    public List<Patient> findAll() {
        return repository.findAll();
    }

    public Optional<Patient> findByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    public Optional<Patient> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public PatientResponseDTO createPatient(PatientCreateDTO patientCreateDTO) {
        Patient patient = patientMapper.toEntity(patientCreateDTO);
        Patient savedPatient = repository.save(patient);
        return patientMapper.toResponseDTO(savedPatient);
    }

    @Override
    public PatientResponseDTO updatePatient(Long id, PatientCreateDTO patientCreateDTO) throws ResourceNotFoundException {
        Patient existingPatient = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));

        patientMapper.updateEntityFromDTO(patientCreateDTO, existingPatient);
        Patient updatedPatient = repository.save(existingPatient);
        return patientMapper.toResponseDTO(updatedPatient);
    }

    @Override
    public PatientResponseDTO findPatientDTOById(Long id) throws ResourceNotFoundException {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
        return patientMapper.toResponseDTO(patient);
    }

    @Override
    public List<PatientResponseDTO> findAllPatientDTOs() {
        return repository.findAll()
                .stream()
                .map(patientMapper::toResponseDTO)
                .collect(Collectors.toList());

    }
}
