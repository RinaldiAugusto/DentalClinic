package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.*;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.User;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.UserRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IAuthService;
import com.augusto.__ClinicaOdontologicaSpringJPA.configuration.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            // Autenticar usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar tokens JWT
            User user = (User) authentication.getPrincipal();
            String jwt = jwtService.generateToken(user);
            String refreshToken = jwtService.generateToken(user); // ✅ DECLARAR refreshToken

            // ✅ ACTUALIZAR USUARIO CON REFRESH TOKEN
            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            // Crear response
            return new AuthResponseDTO(
                    jwt,
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole().toString(),
                    refreshToken  // ✅ AGREGAR ESTE PARÁMETRO
            );

        } catch (Exception e) {
            throw new RuntimeException("Error en login: " + e.getMessage());
        }
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        try {
            // Verificar si el usuario ya existe
            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                throw new RuntimeException("El usuario con email " + registerRequest.getEmail() + " ya existe");
            }

            // Crear nuevo usuario - TEMPORAL: primer usuario como ADMIN
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());

            // TEMPORAL: Si es el primer usuario, hacerlo ADMIN, sino USER
            long userCount = userRepository.count();
            if (userCount == 0) {
                user.setRole(User.Role.ADMIN);
                System.out.println("🎯 PRIMER USUARIO CREADO COMO ADMIN: " + registerRequest.getEmail());
            } else {
                user.setRole(User.Role.USER);
                System.out.println("👤 USUARIO NORMAL CREADO COMO USER: " + registerRequest.getEmail());
            }

            User savedUser = userRepository.save(user);

            // ✅ GENERAR AMBOS TOKENS
            String jwt = jwtService.generateToken(savedUser);
            String refreshToken = jwtService.generateToken(savedUser); // ✅ DECLARAR refreshToken

            // ✅ ACTUALIZAR USUARIO CON REFRESH TOKEN
            savedUser.setRefreshToken(refreshToken);
            userRepository.save(savedUser);

            System.out.println("✅ USUARIO REGISTRADO EXITOSAMENTE: " + savedUser.getEmail() + " - Rol: " + savedUser.getRole());

            // ✅ SOLO UN RETURN CON REFRESH TOKEN
            return new AuthResponseDTO(
                    jwt,
                    savedUser.getEmail(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getRole().toString(),
                    refreshToken  // ✅ AGREGAR ESTE PARÁMETRO
            );

        } catch (Exception e) {
            System.out.println("❌ ERROR EN REGISTRO: " + e.getMessage());
            throw new RuntimeException("Error en registro: " + e.getMessage());
        }
    }

    @Override
    public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequest) {
        try {
            System.out.println("🔄 REFRESH TOKEN REQUEST");

            // ✅ BUSCAR USUARIO POR REFRESH TOKEN
            User user = userRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken())
                    .orElseThrow(() -> {
                        System.out.println("❌ Refresh token not found in database");
                        return new RuntimeException("Invalid refresh token");
                    });

            System.out.println("✅ User found for refresh: " + user.getEmail());

            // ✅ GENERAR NUEVO ACCESS TOKEN (no nuevo refresh token)
            String newAccessToken = jwtService.generateToken(user);

            // ✅ MANTENER EL MISMO REFRESH TOKEN (o generar uno nuevo si prefieres)
            String newRefreshToken = jwtService.generateRefreshToken(user);

            // ✅ ACTUALIZAR EN BASE DE DATOS
            user.setRefreshToken(newRefreshToken);
            userRepository.save(user);

            System.out.println("✅ New tokens generated for: " + user.getEmail());

            return new RefreshTokenResponseDTO(newAccessToken, newRefreshToken);

        } catch (Exception e) {
            System.out.println("❌ Refresh token error: " + e.getMessage());
            throw new RuntimeException("Error refreshing token: " + e.getMessage());
        }
    }

    @Override
    public void logout(RefreshTokenRequestDTO refreshTokenRequest) {
        try {
            System.out.println("🚪 Intentando logout con token: " + refreshTokenRequest.getRefreshToken());

            // Buscar usuario por refresh token y limpiarlo
            userRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken())
                    .ifPresent(user -> {
                        user.setRefreshToken(null);
                        userRepository.save(user);
                        System.out.println("✅ Logout exitoso para: " + user.getEmail());
                    });

        } catch (Exception e) {
            System.out.println("❌ Error en logout: " + e.getMessage());
            throw new RuntimeException("Error during logout: " + e.getMessage());
        }
    }
}
