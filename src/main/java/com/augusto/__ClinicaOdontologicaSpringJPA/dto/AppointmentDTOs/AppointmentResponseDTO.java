package com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTOs;

import java.time.LocalDate;

public class AppointmentResponseDTO {
    private Long id;
    private Long dentistId;
    private Long patientId;
    private LocalDate date;     // ✅ LocalDate directo

    // Constructores, getters y setters...
    public AppointmentResponseDTO() {}

    public AppointmentResponseDTO(Long id, Long dentistId, Long patientId, LocalDate date) {
        this.id = id;
        this.dentistId = dentistId;
        this.patientId = patientId;
        this.date = date;
    }

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}