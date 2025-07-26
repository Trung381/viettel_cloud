package com.example.viettel_cloud.security;

import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Log4j2
@Component
public class JwtTokenProvider {
    public String getPayload(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            log.error("Invalid token");
            return null;
        }

        return new String(Base64.getUrlDecoder().decode(parts[1]));
    }

    public String genTokenHS512(String subId, String jwtSecretKey, long expirationInMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationInMs);
        SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(subId)
                .expiration(expiryDate)
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    public String getSubIdFromTokenHS512(String token, String jwtSecretKey) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }


}
