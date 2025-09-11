package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IDentistService;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dentist")
public class DentistController {

    private final IDentistService iDentistService;

    @Autowired
    public DentistController(IDentistService iDentistService) {
        this.iDentistService = iDentistService;
    }

    @PostMapping
    public Dentist save(@RequestBody Dentist dentist){
        return iDentistService.save(dentist);
    }

    @GetMapping
    public ResponseEntity<List<Dentist>> findAll(){
        return ResponseEntity.ok(iDentistService.findAll());
    }

    @GetMapping("/id/{id}")
    public Optional<Dentist> findById(@PathVariable Long id){
        return iDentistService.findById(id);
    }

    @PutMapping
    public Dentist update(@RequestBody Dentist dentist){
        Optional<Dentist> optionalDentist = iDentistService.findById(dentist.getId());
        if (optionalDentist.isPresent()) {
            iDentistService.update(dentist);
            return dentist;
        }else{
            return dentist;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        iDentistService.delete(id);
        return ResponseEntity.ok("Se elimino el dentista con id: " + id);
    }

}
