package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;


import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.AuthResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.LoginRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.RegisterRequestDTO;

public interface IAuthService {
    AuthResponseDTO login(LoginRequestDTO loginRequest);
    AuthResponseDTO register(RegisterRequestDTO registerRequest);

}