package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.*;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // ✅ AGREGAR ESTO
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        System.out.println("🔐 LOGIN REQUEST for: " + loginRequest.getEmail());
        AuthResponseDTO response = authService.login(loginRequest);
        System.out.println("✅ LOGIN SUCCESS for: " + loginRequest.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        System.out.println("👤 REGISTER REQUEST for: " + registerRequest.getEmail());
        AuthResponseDTO response = authService.register(registerRequest);
        return ResponseEntity.status(201).body(response);
    }

    // ✅ ENDPOINT DE DEBUG CORREGIDO (sin /auth duplicado)
    @GetMapping("/debug-user")
    public ResponseEntity<?> debugCurrentUser(Authentication authentication) {
        System.out.println("🔍 DEBUG USER ENDPOINT CALLED");

        Map<String, Object> response = new HashMap<>();
        if (authentication != null) {
            response.put("authenticated", true);
            response.put("username", authentication.getName());
            response.put("authorities", authentication.getAuthorities().toString());
            response.put("principal", authentication.getPrincipal().getClass().getSimpleName());
            response.put("details", authentication.getDetails() != null ? authentication.getDetails().toString() : "null");

            System.out.println("✅ DEBUG - User authenticated: " + authentication.getName());
            System.out.println("✅ DEBUG - Authorities: " + authentication.getAuthorities());
        } else {
            response.put("authenticated", false);
            response.put("message", "No authentication found");
            System.out.println("❌ DEBUG - No authentication found");
        }
        return ResponseEntity.ok(response);
    }

    // ✅ ENDPOINT PÚBLICO PARA VERIFICAR QUE EL BACKEND FUNCIONA
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        System.out.println("🏥 HEALTH CHECK ENDPOINT CALLED");
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Dental Clinic Backend is running");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
        RefreshTokenResponseDTO response = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
        authService.logout(refreshTokenRequest);
        return ResponseEntity.ok().build();
    }
}