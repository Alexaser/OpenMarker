package com.example.marketOrders.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Токен на 1 час
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //  Получение email из токена
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    //  Получение роли из токена
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    //  Проверка, не истёк ли токен
    public boolean isTokenValid(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }

    //  Получение всех данных из токена
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}