package com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs;

public class AuthResponseDTO {
    private String token;
    private String type = "Bearer";
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    // ✅ NUEVO CAMPO - Refresh Token
    private String refreshToken;

    // Constructores
    public AuthResponseDTO() {}

    // ✅ CONSTRUCTOR ACTUALIZADO con refreshToken
    public AuthResponseDTO(String token, String email, String firstName, String lastName, String role, String refreshToken) {
        this.token = token;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.refreshToken = refreshToken;
    }

    // Constructor anterior (por compatibilidad)
    public AuthResponseDTO(String token, String email, String firstName, String lastName, String role) {
        this.token = token;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // ✅ NUEVO GETTER Y SETTER
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }


}