package com.augusto.__ClinicaOdontologicaSpringJPA.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddressCreateDTO {

    @NotBlank(message = "La calle es obligatoria")
    @Size(min = 3, max = 100, message = "La calle debe tener entre 3 y 100 caracteres")
    private String street;

    @NotBlank(message = "La localidad es obligatoria")
    @Size(min = 3, max = 50, message = "La localidad debe tener entre 3 y 50 caracteres")
    private String location;

    @NotBlank(message = "La provincia es obligatoria")
    @Size(min = 3, max = 50, message = "La provincia debe tener entre 3 y 50 caracteres")
    private String province;

    // Constructores
    public AddressCreateDTO() {}

    public AddressCreateDTO(String street, String location, String province) {
        this.street = street;
        this.location = location;
        this.province = province;
    }

    // Getters y Setters
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
}