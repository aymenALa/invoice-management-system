package com.invoice_management_system.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtUtil {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    @Value("${jwt.expiration:3600}")
    private long expiration;

    public JwtUtil(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(expiration, ChronoUnit.SECONDS))
                .subject(userDetails.getUsername())
                .claim("scope", "ROLE_USER")
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String extractUsername(String token) {
        Jwt jwt = decoder.decode(token);
        return jwt.getClaimAsString("sub");  // 'sub' claim contains the subject (username)
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Jwt jwt = decoder.decode(token);
            return jwt.getClaimAsString("sub").equals(userDetails.getUsername()) && 
                   jwt.getExpiresAt().isAfter(Instant.now());
        } catch (JwtException e) {
            return false;
        }
    }
}