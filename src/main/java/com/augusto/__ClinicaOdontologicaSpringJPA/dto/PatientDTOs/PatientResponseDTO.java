package com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AddressDTOs.AddressDTO;

import java.time.LocalDate;

public class PatientResponseDTO {
    private Long id;
    private String name;
    private String lastName;
    private String cardIdentity;
    private LocalDate admissionOfDate;
    private AddressDTO address;

    // Constructores
    public PatientResponseDTO() {}

    public PatientResponseDTO(Long id, String name, String lastName,
                              String cardIdentity, LocalDate admissionOfDate, AddressDTO address) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.cardIdentity = cardIdentity;
        this.admissionOfDate = admissionOfDate;
        this.address = address;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCardIdentity() { return cardIdentity; }
    public void setCardIdentity(String cardIdentity) { this.cardIdentity = cardIdentity; }

    public LocalDate getAdmissionOfDate() { return admissionOfDate; }
    public void setAdmissionOfDate(LocalDate admissionOfDate) { this.admissionOfDate = admissionOfDate; }

    public AddressDTO getAddress() { return address; }
    public void setAddress(AddressDTO address) { this.address = address; }
}