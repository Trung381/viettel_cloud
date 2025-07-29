package com.example.viettel_cloud.security;

import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
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
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    public boolean validateTokenHS512(final String token, final String jwtSecret) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            String sub = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
            if (StringUtils.isBlank(sub)) {
                throw new Exception(token);
            }
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token. " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token. " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token. " + ex.getMessage());
        } catch (Exception ex) {
            log.error("JWT claims string is empty. " + ex.getMessage());
        }
        return false;
    }
}
