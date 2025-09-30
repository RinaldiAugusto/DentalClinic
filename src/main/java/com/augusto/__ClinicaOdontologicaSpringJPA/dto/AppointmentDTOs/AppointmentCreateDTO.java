package com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AppointmentCreateDTO {

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate date;

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long patientId;

    @NotNull(message = "El ID del dentista es obligatorio")
    private Long dentistId;

    // Constructores y getters/setters...
    public AppointmentCreateDTO() {}
    public AppointmentCreateDTO(LocalDate date, Long patientId, Long dentistId) {
        this.date = date;
        this.patientId = patientId;
        this.dentistId = dentistId;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }
}