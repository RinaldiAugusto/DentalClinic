package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IPatientService;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.PatientRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements IPatientService {
    private PatientRepository repository;

    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient save(Patient patient){
        return repository.save(patient);
    }

    public Optional<Patient> findById(Long id){
        return repository.findById(id);
    }

    public void update(Patient patient){
        repository.save(patient);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Patient> patientOptional = findById(id);

        if (patientOptional.isPresent()){
            repository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("No se pudo eliminar el paciente con id: "+ id);
        }

    }

    public List<Patient> findAll(){
        return repository.findAll();
    }

    public Optional<Patient> findByLastName(String lastName){
        return repository.findByLastName(lastName);
    }

    public Optional<Patient> findByName(String name){
        return repository.findByName(name);
    }

}
