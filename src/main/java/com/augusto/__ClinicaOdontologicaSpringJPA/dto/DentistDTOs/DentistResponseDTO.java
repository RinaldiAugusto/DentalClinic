package com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs;

public class DentistResponseDTO {
    private Long dentistId;
    private String name;
    private String apellido;
    private Integer registration;

    // Constructores, getters y setters...
    public DentistResponseDTO() {}

    public DentistResponseDTO(Long dentistId, String name, String apellido, Integer registration) {
        this.dentistId = dentistId;
        this.name = name;
        this.apellido = apellido;
        this.registration = registration;
    }

    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Integer getRegistration() { return registration; }
    public void setRegistration(Integer registration) { this.registration = registration; }
}