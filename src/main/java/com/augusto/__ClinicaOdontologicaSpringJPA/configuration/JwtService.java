package com.augusto.__ClinicaOdontologicaSpringJPA.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    // SECRET KEY MÁS SIMPLE Y CONFIABLE
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // EXTRAER ROLES CORRECTAMENTE
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        extraClaims.put("roles", roles);

        System.out.println("🎫 JWT SERVICE - Generating token for: " + userDetails.getUsername());
        System.out.println("🎫 JWT SERVICE - Roles: " + roles);
        System.out.println("🎫 JWT SERVICE - Secret Key: " + SECRET_KEY.substring(0, 10) + "...");

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // MÉTODO CORREGIDO PARA EXTRAER ROLES
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return (List<String>) claims.get("roles");
        } catch (Exception e) {
            System.out.println("❌ JWT SERVICE - Error extracting roles: " + e.getMessage());
            return null;
        }
    }

    private Key getSignKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
            System.out.println("🎫 JWT SERVICE - Key bytes length: " + keyBytes.length);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            System.out.println("❌ JWT SERVICE - Error creating sign key: " + e.getMessage());
            throw e;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        System.out.println("🎫 JWT SERVICE - Extracting claims from token");
        System.out.println("🎫 JWT SERVICE - Token: " + token.substring(0, 50) + "...");

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("🎫 JWT SERVICE - Claims extracted successfully");
            System.out.println("🎫 JWT SERVICE - Subject: " + claims.getSubject());
            System.out.println("🎫 JWT SERVICE - Roles: " + claims.get("roles"));

            return claims;
        } catch (Exception e) {
            System.out.println("❌ JWT SERVICE - Error parsing token: " + e.getMessage());
            System.out.println("❌ JWT SERVICE - Error class: " + e.getClass().getName());
            throw e;
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            boolean isValid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
            System.out.println("🎫 JWT SERVICE - Token validation: " + isValid + " for user: " + username);
            return isValid;
        } catch (Exception e) {
            System.out.println("❌ JWT SERVICE - Token validation failed: " + e.getMessage());
            return false;
        }
    }
}