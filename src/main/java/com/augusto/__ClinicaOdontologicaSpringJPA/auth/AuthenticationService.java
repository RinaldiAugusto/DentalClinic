package com.augusto.__ClinicaOdontologicaSpringJPA.auth;

import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.UserRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Role;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.User;
import com.augusto.__ClinicaOdontologicaSpringJPA.configuration.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

}
