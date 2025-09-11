package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IAppointmentService;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IDentistService;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IPatientService;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl.AppointmentServiceImpl;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Appointment;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final IAppointmentService iAppointmentServiceImpl;

    private final IDentistService iDentistService;

    private final IPatientService iPatientService;

    @Autowired
    public AppointmentController(IAppointmentService iAppointmentServiceImpl, IDentistService iDentistService, IPatientService iPatientService) {
        this.iAppointmentServiceImpl = iAppointmentServiceImpl;
        this.iDentistService = iDentistService;
        this.iPatientService = iPatientService;
    }

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
            //seteamos al ResponseEntity con el código 200 y le agregamos el turno dto como cuerpo de la respuesta
            response = ResponseEntity.ok(iAppointmentServiceImpl.update(appointmentDTO));

        } else {
            //uno no existe, entonces bloqueamos la operación
            //setear al ResponseEntity el código 400
            response = ResponseEntity.badRequest().build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException{
        iAppointmentServiceImpl.delete(id);
        return ResponseEntity.ok("Se elimino el turno con id: " + id);
    }
}
