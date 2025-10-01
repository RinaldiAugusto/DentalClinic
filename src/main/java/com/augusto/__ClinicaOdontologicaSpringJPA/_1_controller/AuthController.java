package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.AuthResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.LoginRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.RegisterRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        AuthResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        AuthResponseDTO response = authService.register(registerRequest);
        return ResponseEntity.status(201).body(response);
    }

    // Endpoint simple para verificar que el auth funciona
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth endpoints are working!");
    }
}
