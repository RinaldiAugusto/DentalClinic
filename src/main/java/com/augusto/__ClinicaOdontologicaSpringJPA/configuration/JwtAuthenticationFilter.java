package com.augusto.__ClinicaOdontologicaSpringJPA.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.validateToken(jwt, userDetails)) {
                // EXTRAER ROLES DEL TOKEN - CON MANEJO ROBUSTO
                List<String> roles = jwtService.extractRoles(jwt);

                var authorities = roles != null ? roles.stream()
                        .map(role -> {
                            // Asegurar que el rol tenga el formato correcto
                            if (role.startsWith("ROLE_")) {
                                return new SimpleGrantedAuthority(role);
                            } else {
                                return new SimpleGrantedAuthority("ROLE_" + role);
                            }
                        })
                        .collect(Collectors.toList()) : userDetails.getAuthorities().stream()
                        .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                        .collect(Collectors.toList());

                // DEBUG DETALLADO
                System.out.println("=== 🔐 JWT FILTER DEBUG ===");
                System.out.println("📧 User: " + userEmail);
                System.out.println("🎭 Roles from token: " + roles);
                System.out.println("🔑 Final authorities: " + authorities);
                System.out.println("🌐 Request URI: " + request.getRequestURI());
                System.out.println("🛡️ UserDetails authorities: " + userDetails.getAuthorities());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        authorities
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("✅ Authentication set successfully!");
                System.out.println("=============================");
            } else {
                System.out.println("❌ JWT FILTER - Token validation failed for user: " + userEmail);
            }
        }
        filterChain.doFilter(request, response);
    }
}