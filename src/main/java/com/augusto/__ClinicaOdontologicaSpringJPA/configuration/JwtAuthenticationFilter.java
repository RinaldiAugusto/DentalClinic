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

        System.out.println("=== 🔍 JWT FILTER START ===");
        System.out.println("🌐 Request: " + request.getMethod() + " " + request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("⚠️ No Bearer token found - proceeding without authentication");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        System.out.println("📧 Extracted user from token: " + userEmail);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                System.out.println("👤 UserDetails loaded: " + userDetails.getUsername());

                if (jwtService.validateToken(jwt, userDetails)) {
                    List<String> roles = jwtService.extractRoles(jwt);

                    var authorities = roles != null ? roles.stream()
                            .map(role -> {
                                if (role.startsWith("ROLE_")) {
                                    return new SimpleGrantedAuthority(role);
                                } else {
                                    return new SimpleGrantedAuthority("ROLE_" + role);
                                }
                            })
                            .collect(Collectors.toList()) : userDetails.getAuthorities().stream()
                            .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                            .collect(Collectors.toList());

                    System.out.println("🎭 Final authorities: " + authorities);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // ✅ ✅ ✅ AGREGÁ EL DEBUG EXTREMO JUSTO AQUÍ ✅ ✅ ✅
                    System.out.println("=== 🚨 DEBUG EXTREMO - ROLES ===");
                    System.out.println("📧 User: " + userEmail);
                    System.out.println("🎭 Roles from token: " + roles);
                    System.out.println("🔑 Final Authorities: " + authorities);
                    System.out.println("🛡️ Security Context Auth: " + SecurityContextHolder.getContext().getAuthentication());
                    System.out.println("🔍 Is Authenticated: " + (SecurityContextHolder.getContext().getAuthentication() != null));
                    if (SecurityContextHolder.getContext().getAuthentication() != null) {
                        System.out.println("🎯 Authorities in Context: " +
                                SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                    }
                    System.out.println("=================================");

                    System.out.println("✅ Authentication SUCCESS for: " + userEmail);
                } else {
                    System.out.println("❌ Token validation FAILED for: " + userEmail);
                }
            } catch (Exception e) {
                System.out.println("💥 ERROR in JWT filter: " + e.getMessage());
                // No bloquear la request, solo continuar sin autenticación
            }
        } else {
            System.out.println("ℹ️ Authentication already exists or no user email");
        }

        System.out.println("=== 🔍 JWT FILTER END ===");
        filterChain.doFilter(request, response);
    }
}