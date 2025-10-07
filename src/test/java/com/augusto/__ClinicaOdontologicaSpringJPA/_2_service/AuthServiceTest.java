package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl.AuthService;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.UserRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.User;
import com.augusto.__ClinicaOdontologicaSpringJPA.configuration.JwtService;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.LoginRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.RegisterRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.AuthResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private RegisterRequestDTO registerRequest;
    private LoginRequestDTO loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequestDTO();
        registerRequest.setEmail("test@dental.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("Test");
        registerRequest.setLastName("User");

        loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("test@dental.com");
        loginRequest.setPassword("password123");

        user = User.builder()
                .id(1L)
                .email("test@dental.com")
                .firstName("Test")
                .lastName("User")
                .password("encodedPassword")
                .role(User.Role.ADMIN)
                .build();
    }

    @Test
    void testRegister_Success() {
        // Given
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(any())).thenReturn("refreshToken");
        when(userRepository.count()).thenReturn(0L);

        // When
        AuthResponseDTO response = authService.register(registerRequest);

        // Then
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals("test@dental.com", response.getEmail());
        assertEquals("Test", response.getFirstName());
        assertEquals("User", response.getLastName());

        verify(userRepository, times(1)).findByEmail(registerRequest.getEmail());
        verify(userRepository, times(2)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
    }

    @Test
    void testRegister_UserAlreadyExists() {
        // Given
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.register(registerRequest));

        assertTrue(exception.getMessage().contains("ya existe"));
        verify(userRepository, never()).save(any(User.class));
    }
}