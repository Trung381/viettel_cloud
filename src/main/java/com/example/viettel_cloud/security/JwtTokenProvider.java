package com.example.viettel_cloud.security;

import com.example.viettel_cloud.dto.response.UserIdentityRes;
import com.example.viettel_cloud.util.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Log4j2
@Component
public class JwtTokenProvider {
    public UserIdentityRes getUserIdentity(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            log.error("Invalid token");
            return null;
        }

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        return Util.stringToObject(UserIdentityRes.class, payload);
    }
}
