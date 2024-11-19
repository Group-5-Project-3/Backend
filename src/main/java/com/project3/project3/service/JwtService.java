package com.project3.project3.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final SecretKey SECRET_KEY;

    public JwtService() {
        String secretKey = System.getenv("JWT_SECRET");
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        this.SECRET_KEY = Keys.hmacShaKeyFor(decodedKey);
    }

    // Generate JWT token with roles and user ID as the subject
    public String generateToken(String userId, Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)  // Convert authorities to role strings
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userId)  // Use userId as the subject
                .claim("roles", roles)  // Store roles as List<String>
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))  // 24-hour expiration
                .signWith(SECRET_KEY)
                .compact();
    }

    // Extract user ID from token (now the subject)
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);  // Extract userId as the subject
    }

    // Extract roles from token as a list of GrantedAuthority
    public List<GrantedAuthority> extractRoles(String token) {
        List<String> roles = extractClaim(token, claims -> claims.get("roles", List.class));  // Extract roles as List<String>
        return roles.stream()
                .map(role -> (GrantedAuthority) () -> role)  // Convert each role string to GrantedAuthority
                .collect(Collectors.toList());
    }

    // Validate token
    public boolean validateToken(String token, String userId) {
        final String extractedUserId = extractUserId(token);
        return (extractedUserId.equals(userId) && !isTokenExpired(token));
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // Extract specific claim from token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .setAllowedClockSkewSeconds(2)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

