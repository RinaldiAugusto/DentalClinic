package com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DentistCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastName;

    @NotNull(message = "La matrícula es obligatoria")
    private Integer registration;

    // Constructores, getters y setters...
    public DentistCreateDTO() {}

    public DentistCreateDTO(String name, String lastName, Integer registration) {
        this.name = name;
        this.lastName = lastName;
        this.registration = registration;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getApellido() { return lastName; }
    public void setLastName(String apellido) { this.lastName = apellido; }

    public Integer getRegistration() { return registration; }
    public void setRegistration(Integer registration) { this.registration = registration; }
}