package com.trafficnavigator.traffic_backend.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET =
            "traffic-secret-key-12345678901234567890123456789012";
    private final Key key = Keys.hmacShaKeyFor(
            "traffic-secret-key-12345678901234567890123456789012".getBytes()
    );

    public String generateToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(key)
                .compact();

    }
    public String extractEmail(String token){
        return Jwts.parser()
                .verifyWith((SecretKey)key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }
}
