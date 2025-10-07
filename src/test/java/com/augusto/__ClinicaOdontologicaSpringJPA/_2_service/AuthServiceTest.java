package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl.AuthService;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.UserRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.User;
import com.augusto.__ClinicaOdontologicaSpringJPA.configuration.JwtService;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.LoginRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.RegisterRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.AuthResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.RefreshTokenRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.RefreshTokenResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Mock
    private Authentication authentication;

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
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals("test@dental.com", response.getEmail());
        assertEquals("Test", response.getFirstName());
        assertEquals("User", response.getLastName());

        // ✅ CORREGIDO: Se llama save() 2 veces (una para crear usuario, otra para actualizar refresh token)
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

    @Test
    void testLogin_Success() {
        // Given
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(any())).thenReturn("refreshToken");
        when(userRepository.save(any())).thenReturn(user);

        // When
        AuthResponseDTO response = authService.login(loginRequest);

        // Then
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals("test@dental.com", response.getEmail());

        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtService, times(1)).generateToken(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testLogin_InvalidCredentials() {
        // Given
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login(loginRequest));

        assertTrue(exception.getMessage().contains("Error en login"));
        verify(userRepository, never()).findByEmail(any());
    }

    @Test
    void testRefreshToken_Success() {
        // Given
        RefreshTokenRequestDTO refreshRequest = new RefreshTokenRequestDTO();
        refreshRequest.setRefreshToken("validRefreshToken");

        // ✅ CORREGIDO: Establecer el refreshToken en el usuario
        user.setRefreshToken("validRefreshToken");

        when(userRepository.findByRefreshToken(any())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("newJwtToken");
        when(jwtService.generateRefreshToken(any())).thenReturn("newRefreshToken");
        when(userRepository.save(any())).thenReturn(user);

        // When
        RefreshTokenResponseDTO response = authService.refreshToken(refreshRequest);

        // Then
        assertNotNull(response);
        assertEquals("newJwtToken", response.getAccessToken());
        assertEquals("newRefreshToken", response.getRefreshToken());

        verify(userRepository, times(1)).findByRefreshToken("validRefreshToken");
        verify(jwtService, times(1)).generateToken(user);
    }

    @Test
    void testRefreshToken_InvalidToken() {
        // Given
        RefreshTokenRequestDTO refreshRequest = new RefreshTokenRequestDTO();
        refreshRequest.setRefreshToken("invalidToken");

        when(userRepository.findByRefreshToken(any())).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.refreshToken(refreshRequest));

        assertTrue(exception.getMessage().contains("Invalid refresh token"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLogout_Success() {
        // Given
        RefreshTokenRequestDTO logoutRequest = new RefreshTokenRequestDTO();
        logoutRequest.setRefreshToken("validRefreshToken");

        when(userRepository.findByRefreshToken(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        // When
        authService.logout(logoutRequest);

        // Then
        verify(userRepository, times(1)).findByRefreshToken("validRefreshToken");
        verify(userRepository, times(1)).save(user);
        // Verificar que el refresh token se estableció como null
        assertNull(user.getRefreshToken());
    }

    @Test
    void testLogout_TokenNotFound() {
        // Given
        RefreshTokenRequestDTO logoutRequest = new RefreshTokenRequestDTO();
        logoutRequest.setRefreshToken("nonexistentToken");

        when(userRepository.findByRefreshToken(any())).thenReturn(Optional.empty());

        // When - No debería lanzar excepción
        assertDoesNotThrow(() -> authService.logout(logoutRequest));

        // Then
        verify(userRepository, times(1)).findByRefreshToken("nonexistentToken");
        verify(userRepository, never()).save(any(User.class));
    }
}