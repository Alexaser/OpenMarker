package com.example.marketOrders.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String email, UUID uuid) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(email + "|" + uuid.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Токен на 1 час
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //  Получение email из токена
    public String extractEmail(String token) {
        String subject = getClaims(token).getSubject();
        return subject.split("\\|")[0]; //  Разделяем email и UUID
    }

    //  Получение роли из токена
    public UUID extractUuid(String token) {
        String subject = getClaims(token).getSubject();
        return UUID.fromString(subject.split("\\|")[1]); //  Разделяем email и UUID
    }
    //  Проверка, не истёк ли токен
    public boolean isTokenValid(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }

    //  Получение всех данных из токена
    private Claims getClaims(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}