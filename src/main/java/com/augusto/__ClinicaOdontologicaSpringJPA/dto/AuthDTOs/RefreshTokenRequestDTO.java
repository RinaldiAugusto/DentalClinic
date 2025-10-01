package com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs;

public class RefreshTokenRequestDTO {
    private String refreshToken;

    // Constructores
    public RefreshTokenRequestDTO() {}

    public RefreshTokenRequestDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Getters y Setters
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}