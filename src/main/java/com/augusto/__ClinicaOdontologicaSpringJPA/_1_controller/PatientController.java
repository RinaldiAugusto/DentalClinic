package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IPatientService;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl.PatientServiceImpl;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs.PatientCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs.PatientResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RequestMapping("/patient")
@RestController
public class PatientController {

    private final IPatientService iPatientService;

    public PatientController(IPatientService iPatientService) {
        this.iPatientService = iPatientService;
    }

    @Autowired
    public PatientController(PatientServiceImpl iPatientService) {
        this.iPatientService = iPatientService;
    }


    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientCreateDTO patientCreateDTO) {
        PatientResponseDTO createdPatient = iPatientService.createPatient(patientCreateDTO);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<PatientResponseDTO> patients = iPatientService.findAllPatientDTOs();
        return ResponseEntity.ok(patients);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) throws ResourceNotFoundException {
        PatientResponseDTO patient = iPatientService.findPatientDTOById(id);
        return ResponseEntity.ok(patient);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientCreateDTO patientCreateDTO) throws ResourceNotFoundException {
        PatientResponseDTO updatedPatient = iPatientService.updatePatient(id, patientCreateDTO);
        return ResponseEntity.ok(updatedPatient);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        iPatientService.delete(id);
        return ResponseEntity.ok("Se elimino el paciente con id: " + id);
    }

    @GetMapping("/lastName/{lastName}")
    public Patient findByLastName(@PathVariable String lastName){
        Optional<Patient> optionalPatient = iPatientService.findByLastName(lastName);
        if (optionalPatient.isPresent()){
            return optionalPatient.get();
        }else{
            return null;
        }
    }

    @GetMapping("/name/{name}")
    public Patient findByName(@PathVariable String name){
        Optional<Patient> optionalPatient = iPatientService.findByName(name);
        if (optionalPatient.isPresent()){
            return optionalPatient.get();
        }else {
            return null;
        }
    }

}
