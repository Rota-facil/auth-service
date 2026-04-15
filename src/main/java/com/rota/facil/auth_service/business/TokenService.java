package com.rota.facil.auth_service.business;

import com.rota.facil.auth_service.http.dto.response.AccessTokenResponseDTO;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {
    private  Long EXPIRATION_TIME = 86400000L;

    private final  PrivateKey privateKey;

    public String generateAccessToken(UserEntity saved) {
        return Jwts.builder()
                .subject(saved.getEmail())
                .claim("userId", saved.getId())
                .claim("prefectureId", saved.getPrefecture().getId())
                .claim("role", saved.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(privateKey)
                .compact();
    }
}
