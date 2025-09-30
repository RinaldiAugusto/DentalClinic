package com.augusto.__ClinicaOdontologicaSpringJPA.dto.AddressDTOs;

public class AddressDTO {
    private Long id;
    private String street;
    private String location;
    private String province;

    // Constructores
    public AddressDTO() {}

    public AddressDTO(Long id, String street, String location, String province) {
        this.id = id;
        this.street = street;
        this.location = location;
        this.province = province;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
}