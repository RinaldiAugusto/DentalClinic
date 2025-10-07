package com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs;

public class DentistResponseDTO {
    private Long id;
    private String name;
    private String lastName;
    private Integer registration;


    // Constructores, getters y setters...
    public DentistResponseDTO() {}

    public DentistResponseDTO(Long id, String name, String lastName, Integer registration) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.registration = registration;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Integer getRegistration() { return registration; }
    public void setRegistration(Integer registration) { this.registration = registration; }
}