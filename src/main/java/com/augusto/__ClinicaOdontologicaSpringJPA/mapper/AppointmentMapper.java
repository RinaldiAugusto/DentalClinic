package com.augusto.__ClinicaOdontologicaSpringJPA.mapper;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Appointment;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IPatientService;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IDentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class AppointmentMapper {

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IDentistService dentistService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // ✅ CONVERSIÓN ENTRE DTO VIEJO Y NUEVO

    // AppointmentDTO (viejo) → AppointmentResponseDTO (nuevo)
    public AppointmentResponseDTO toResponseDTO(AppointmentDTO oldDTO) {
        if (oldDTO == null) return null;

        LocalDate date = LocalDate.parse(oldDTO.getDate(), formatter);

        return new AppointmentResponseDTO(
                oldDTO.getId(),
                oldDTO.getDentist_id(),
                oldDTO.getPatient_id(),
                date
        );
    }

    // AppointmentResponseDTO (nuevo) → AppointmentDTO (viejo)
    public AppointmentDTO toOldDTO(AppointmentResponseDTO newDTO) {
        if (newDTO == null) return null;

        String dateString = newDTO.getDate().format(formatter);

        return new AppointmentDTO(
                newDTO.getId(),
                newDTO.getDentistId(),
                newDTO.getPatientId(),
                dateString
        );
    }

    // ✅ MÉTODOS PARA LOS NUEVOS DTOs

    public AppointmentResponseDTO toResponseDTO(Appointment appointment) {
        if (appointment == null) return null;

        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getDentist() != null ? appointment.getDentist().getId() : null,
                appointment.getPatient() != null ? appointment.getPatient().getId() : null,
                appointment.getDate()
        );
    }

    public Appointment toEntity(AppointmentCreateDTO createDTO) {
        if (createDTO == null) return null;

        Appointment appointment = new Appointment();
        appointment.setDate(createDTO.getDate());

        if (createDTO.getPatientId() != null) {
            Patient patient = patientService.findById(createDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            appointment.setPatient(patient);
        }

        if (createDTO.getDentistId() != null) {
            Dentist dentist = dentistService.findById(createDTO.getDentistId())
                    .orElseThrow(() -> new RuntimeException("Dentista no encontrado"));
            appointment.setDentist(dentist);
        }

        return appointment;
    }
}