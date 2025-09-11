package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IPatientService;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl.PatientServiceImpl;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/patient")
@RestController
public class PatientController {

    private final IPatientService iPatientService;

    @Autowired
    public PatientController(PatientServiceImpl iPatientService) {
        this.iPatientService = iPatientService;
    }


    @PostMapping
    public Patient save(@RequestBody Patient patient){
        return iPatientService.save(patient);
    }

    @GetMapping
    public List<Patient> findAll(){
        return iPatientService.findAll();
    }

    @GetMapping("/id")
    public Optional<Patient> findById(@RequestParam Long id){
        return iPatientService.findById(id);
    }

    @PutMapping
    public Patient update(@RequestBody Patient patient){
        Optional<Patient>  optionalPatient = iPatientService.findById(patient.getId());
        if (optionalPatient.isPresent()){
            iPatientService.update(patient);
            return patient;
        }else{
            return patient;
        }
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
