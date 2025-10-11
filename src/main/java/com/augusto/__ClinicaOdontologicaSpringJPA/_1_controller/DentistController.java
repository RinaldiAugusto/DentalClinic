package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IDentistService;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dentists")
public class DentistController {

    private final IDentistService iDentistService;

    @Autowired
    public DentistController(IDentistService iDentistService) {
        this.iDentistService = iDentistService;
    }

    //CREAR
    @PostMapping
    public ResponseEntity<DentistResponseDTO> createDentist(@Valid @RequestBody DentistCreateDTO dentistCreateDTO) {
        DentistResponseDTO createdDentist = iDentistService.createDentist(dentistCreateDTO);
        return new ResponseEntity<>(createdDentist, HttpStatus.CREATED);
    }

    //LISTAR A TODOS
    @GetMapping
    public ResponseEntity<List<DentistResponseDTO>> getAllDentists() {
        List<DentistResponseDTO> dentists = iDentistService.findAllDentistDTOs();
        return ResponseEntity.ok(dentists);
    }

    //BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<DentistResponseDTO> getDentistById(@PathVariable Long id) throws ResourceNotFoundException {
        DentistResponseDTO dentist = iDentistService.findDentistDTOById(id);
        return ResponseEntity.ok(dentist);
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<DentistResponseDTO> updateDentist(
            @PathVariable Long id,
            @Valid @RequestBody DentistCreateDTO dentistCreateDTO) throws ResourceNotFoundException {

        DentistResponseDTO updatedDentist = iDentistService.updateDentist(id, dentistCreateDTO);
        return ResponseEntity.ok(updatedDentist);
    }

    //BORRAR POR ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        iDentistService.delete(id);
        return ResponseEntity.ok("Se elimino el dentista con id: " + id);
    }

}
