package com.hotel.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.hotel.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(
                "my-hotel-management-secret-key-2026-very-secure"
                .getBytes()
            );

    private final long expirationTime = 1000 * 60 * 60;

    public String generateToken(User user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(
                    new Date(System.currentTimeMillis() + expirationTime)
                )
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractRole(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public boolean isTokenValid(String token) {

        try {

            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}