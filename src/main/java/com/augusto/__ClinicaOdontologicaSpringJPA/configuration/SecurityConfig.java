package com.augusto.__ClinicaOdontologicaSpringJPA.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll() // Si usas H2 console

                        // PROTECCIÓN POR ROLES - FORMATO CORRECTO
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Sin "ROLE_" prefix aquí!

                        .requestMatchers("/patients/create", "/patients/update/**", "/patients/delete/**").hasRole("ADMIN")
                        .requestMatchers("/patients/**").hasAnyRole("ADMIN", "USER") // Sin "ROLE_" prefix!

                        .requestMatchers("/dentists/create", "/dentists/update/**", "/dentists/delete/**").hasRole("ADMIN")
                        .requestMatchers("/dentists/**").hasAnyRole("ADMIN", "USER")

                        .requestMatchers("/appointments/create", "/appointments/update/**", "/appointments/delete/**").hasRole("ADMIN")
                        .requestMatchers("/appointments/**").hasAnyRole("ADMIN", "USER")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}