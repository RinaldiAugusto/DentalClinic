package com.augusto.__ClinicaOdontologicaSpringJPA.auth;

import lombok.*;
import org.antlr.v4.runtime.Token;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
}
