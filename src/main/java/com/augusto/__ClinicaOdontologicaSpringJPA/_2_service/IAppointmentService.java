package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs.AppointmentResponseDTO;
import java.util.List;
import java.util.List;
import java.util.Optional;

public interface IAppointmentService {
    AppointmentDTO save(AppointmentDTO appointmentDTO);
    Optional<AppointmentDTO> findById(Long id);
    AppointmentDTO update(AppointmentDTO appointment) throws Exception;
    Optional<AppointmentDTO> delete(Long id) throws ResourceNotFoundException;
    List<AppointmentDTO> findAll();
    AppointmentResponseDTO createAppointment(AppointmentCreateDTO appointmentCreateDTO);
    AppointmentResponseDTO updateAppointment(Long id, AppointmentCreateDTO appointmentCreateDTO);
    AppointmentResponseDTO findAppointmentResponseById(Long id);
    List<AppointmentResponseDTO> findAllAppointmentResponses();
}
