package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Role;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.AuthResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.LoginRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AuthDTOs.RegisterRequestDTO;
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
                user.getLastName()
        );
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        // Verificar si el usuario ya existe
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        // Crear nuevo usuario
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setRole(Role.USER); // Rol por defecto

        User savedUser = userRepository.save(user);

        // Generar token JWT
        String jwt = jwtService.generateToken(savedUser);

        // Crear response
        return new AuthResponseDTO(
                jwt,
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName()
        );
    }
}
