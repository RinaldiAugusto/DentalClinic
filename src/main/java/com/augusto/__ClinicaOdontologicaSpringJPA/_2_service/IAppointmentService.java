package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Appointment;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IAppointmentService {
    AppointmentDTO save(AppointmentDTO appointmentDTO);
    Optional<AppointmentDTO> findById(Long id);
    AppointmentDTO update(AppointmentDTO appointment) throws Exception;
    Optional<AppointmentDTO> delete(Long id) throws ResourceNotFoundException;
    List<AppointmentDTO> findAll();
}
