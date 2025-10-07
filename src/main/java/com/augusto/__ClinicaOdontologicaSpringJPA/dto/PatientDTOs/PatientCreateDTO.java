package com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AddressDTOs.AddressCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AddressDTOs.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class PatientCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastName;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 7, max = 10, message = "El DNI debe tener entre 7 y 10 caracteres")
    private String cardIdentity;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDate admissionOfDate;

    @Valid
    @NotNull(message = "La dirección es obligatoria")
    private AddressCreateDTO address;

    // Constructor vacío
    public PatientCreateDTO() {}

    // Constructor con todos los campos
    public PatientCreateDTO(String name, String lastName, String cardIdentity,
                            LocalDate admissionOfDate, AddressCreateDTO address) {
        this.name = name;
        this.lastName = lastName;
        this.cardIdentity = cardIdentity;
        this.admissionOfDate = admissionOfDate;
        this.address = address;
    }

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCardIdentity() { return cardIdentity; }
    public void setCardIdentity(String cardIdentity) { this.cardIdentity = cardIdentity; }

    public LocalDate getAdmissionOfDate() { return admissionOfDate; }
    public void setAdmissionOfDate(LocalDate admissionOfDate) { this.admissionOfDate = admissionOfDate; }

    public AddressCreateDTO getAddress() { return address; }
    public void setAddress(AddressDTO address) { this.address = address; }
}
