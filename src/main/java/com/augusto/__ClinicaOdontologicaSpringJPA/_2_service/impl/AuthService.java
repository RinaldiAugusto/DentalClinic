package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Role;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.*;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.User;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.UserRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IAuthService;
import com.augusto.__ClinicaOdontologicaSpringJPA.configurationJWT.JwtService;
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

            // Generar token JWT
            User user = (User) authentication.getPrincipal();
            String jwt = jwtService.generateToken(user);

            // Crear response
            return new AuthResponseDTO(
                    jwt,
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole().toString()
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

            // Generar token JWT
            String jwt = jwtService.generateToken(savedUser);

            // Crear response
            AuthResponseDTO response = new AuthResponseDTO(
                    jwt,
                    savedUser.getEmail(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getRole().toString()
            );

            System.out.println("✅ USUARIO REGISTRADO EXITOSAMENTE: " + savedUser.getEmail() + " - Rol: " + savedUser.getRole());

            return response;

        } catch (Exception e) {
            System.out.println("❌ ERROR EN REGISTRO: " + e.getMessage());
            throw new RuntimeException("Error en registro: " + e.getMessage());
        }
    }
    @Override
    public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequest) {
        try {
            System.out.println("🔄 Intentando refresh token: " + refreshTokenRequest.getRefreshToken());

            // Buscar usuario por refresh token - ✅ CORREGIDO: paréntesis cerrado
            User user = userRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken())
                    .orElseThrow(() -> {
                        System.out.println("❌ Refresh token no encontrado en BD");
                        return new RuntimeException("Invalid refresh token");
                    }); // ✅ AQUÍ ESTABA FALTANDO CERRAR ESTE PARÉNTESIS

            System.out.println("✅ Usuario encontrado: " + user.getEmail());

            // Validar que el refresh token coincida - ✅ CORREGIDO: lógica invertida
            if (!user.getRefreshToken().equals(refreshTokenRequest.getRefreshToken())) {
                System.out.println("❌ Refresh token no coincide");
                throw new RuntimeException("Invalid refresh token");
            }

            // Generar nuevos tokens
            String newAccessToken = jwtService.generateToken(user);
            String newRefreshToken = jwtService.generateRefreshToken(user); // Esto dará error hasta que creemos el método

            // Actualizar refresh token en base de datos
            user.setRefreshToken(newRefreshToken);
            userRepository.save(user);

            System.out.println("✅ Nuevos tokens generados para: " + user.getEmail());

            return new RefreshTokenResponseDTO(newAccessToken, newRefreshToken);

        } catch (Exception e) {
            System.out.println("❌ Error en refresh token: " + e.getMessage());
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
