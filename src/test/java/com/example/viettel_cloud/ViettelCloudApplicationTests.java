package com.example.viettel_cloud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.viettel_cloud.util.Util.*;

@SpringBootTest
class ViettelCloudApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateCodeVerifier(){
        String codeVerifier = generateCodeVerifier();
        System.out.println(codeVerifier);
    }

    @Test
    void testSHA256() {
        String codeVerifier = "yr5uQh4tzX0jWvhbNZHh65CN1tR8PAZD69fRiS5fxR7r.6O5dIcAvSFu7fgOQ2hnEna5WF3Hii.escH-qyBaGvmLV-HQDWhU-ghCoZ-65EL8.31BopbQ7amvIu_gB";
        String codeVerifierHash256 = hashSHA256(codeVerifier);
        System.out.println(codeVerifierHash256);
    }

    @Test
    void testUrlBase64(){
        String codeVerifierSHA256 = "YmYzYTQxMDc5YmJhNWUzNjkwMjc5ZTQ2ZThmN2JjNmM4NDQ4ODcwZmUyMTUyMDUzZjIwNTNiYTNhZjIxODZkZQ";
        String urlBase64 = base64UrlEncode(codeVerifierSHA256);
        System.out.println(urlBase64);
    }

    @Test
    void testCreateCodeChallengeFromCodeVerifier(){
        String codeChallenge = "YmYzYTQxMDc5YmJhNWUzNjkwMjc5ZTQ2ZThmN2JjNmM4NDQ4ODcwZmUyMTUyMDUzZjIwNTNiYTNhZjIxODZkZQ";
        String codeVerifier = "yr5uQh4tzX0jWvhbNZHh65CN1tR8PAZD69fRiS5fxR7r.6O5dIcAvSFu7fgOQ2hnEna5WF3Hii.escH-qyBaGvmLV-HQDWhU-ghCoZ-65EL8.31BopbQ7amvIu_gB";
        String hehe = genCodeChallenge(codeVerifier);
        System.out.println(hehe.equals(codeChallenge));
    }
}
