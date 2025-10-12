package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IAppointmentService;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IDentistService;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IPatientService;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final IAppointmentService iAppointmentServiceImpl;
    private final IDentistService iDentistService;
    private final IPatientService iPatientService;

    @Autowired
    public AppointmentController(IAppointmentService iAppointmentServiceImpl,
                                 IDentistService iDentistService,
                                 IPatientService iPatientService) {
        this.iAppointmentServiceImpl = iAppointmentServiceImpl;
        this.iDentistService = iDentistService;
        this.iPatientService = iPatientService;
    }

    // ========== VERSIÓN ORIGINAL (DTO viejo) ==========

    @PostMapping
    public ResponseEntity<AppointmentDTO> save(@RequestBody AppointmentDTO appointmentDTO){
        ResponseEntity<AppointmentDTO> response;

        if (appointmentDTO.getDentist_id() != null && appointmentDTO.getPatient_id() != null){
            response = ResponseEntity.ok(iAppointmentServiceImpl.save(appointmentDTO));
        }else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> findAll(){
        return ResponseEntity.ok(iAppointmentServiceImpl.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id){
        Optional<AppointmentDTO> appointmentDTOOptional = iAppointmentServiceImpl.findById(id);

        if (appointmentDTOOptional.isPresent()){
            return ResponseEntity.ok(appointmentDTOOptional.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<AppointmentDTO> update(@RequestBody AppointmentDTO appointmentDTO) throws Exception {
        ResponseEntity<AppointmentDTO> response;

        //chequeamos que existan el odontólogo y el paciente
        if (iDentistService.findById(appointmentDTO.getDentist_id()).isPresent()
                && iPatientService.findById(appointmentDTO.getPatient_id()).isPresent()) {
            //ambos existen en la DB
            response = ResponseEntity.ok(iAppointmentServiceImpl.update(appointmentDTO));
        } else {
            //uno no existe, entonces bloqueamos la operación
            response = ResponseEntity.badRequest().build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException{
        iAppointmentServiceImpl.delete(id);
        return ResponseEntity.ok("Se elimino el turno con id: " + id);
    }

    // ========== VERSIÓN V2 (Nuevos DTOs) ==========

    @PostMapping("/v2")
    public ResponseEntity<AppointmentResponseDTO> createAppointmentV2(@Valid @RequestBody AppointmentCreateDTO appointmentCreateDTO) {
        AppointmentResponseDTO createdAppointment = iAppointmentServiceImpl.createAppointment(appointmentCreateDTO);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    // GET ALL con nuevo DTO
    @GetMapping("/v2")
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointmentsV2() {
        List<AppointmentResponseDTO> appointments = iAppointmentServiceImpl.findAllAppointmentResponses();
        return ResponseEntity.ok(appointments);
    }

    // GET BY ID con nuevo DTO
    @GetMapping("/v2/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentByIdV2(@PathVariable Long id) {
        AppointmentResponseDTO appointment = iAppointmentServiceImpl.findAppointmentResponseById(id);
        return ResponseEntity.ok(appointment);
    }

    // PUT con nuevo DTO
    @PutMapping("/v2/{id}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointmentV2(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentCreateDTO appointmentCreateDTO) {
        AppointmentResponseDTO updatedAppointment = iAppointmentServiceImpl.updateAppointment(id, appointmentCreateDTO);
        return ResponseEntity.ok(updatedAppointment);
    }

    // ✅ DELETE V2 - FALTANTE - ESTE ES EL QUE NECESITA TU FRONTEND
    @DeleteMapping("/v2/{id}")
    public ResponseEntity<String> deleteAppointmentV2(@PathVariable Long id) {
        try {
            iAppointmentServiceImpl.deleteAppointment(id);
            return ResponseEntity.ok("Turno eliminado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}