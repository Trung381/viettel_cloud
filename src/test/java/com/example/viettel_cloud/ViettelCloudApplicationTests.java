package com.example.viettel_cloud;

import com.example.viettel_cloud.other_service.viettel_cloud_iam.ViettelCloudIAMService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.viettel_cloud.util.Util.*;

@SpringBootTest
class ViettelCloudApplicationTests {
    @Autowired
    private ViettelCloudIAMService viettelCloudIAMService;

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateCodeVerifier(){
        String codeVerifier = generateCodeVerifier();
        System.out.println(codeVerifier);
    }

    @Test
    void testCreateCodeChallengeFromCodeVerifier(){
        String codeChallenge = "YmYzYTQxMDc5YmJhNWUzNjkwMjc5ZTQ2ZThmN2JjNmM4NDQ4ODcwZmUyMTUyMDUzZjIwNTNiYTNhZjIxODZkZQ";
        String codeVerifier = "yr5uQh4tzX0jWvhbNZHh65CN1tR8PAZD69fRiS5fxR7r.6O5dIcAvSFu7fgOQ2hnEna5WF3Hii.escH-qyBaGvmLV-HQDWhU-ghCoZ-65EL8.31BopbQ7amvIu_gB";
        String hehe = genCodeChallenge(codeVerifier);
        System.out.println(hehe.equals(codeChallenge));
    }

    @Test
    void testInitLoginLink(){
        System.out.println(viettelCloudIAMService.genLoginLink());
    }

    @Test
    void testGenState() {
        String state = genState();
        System.out.println(state);
    }
}
