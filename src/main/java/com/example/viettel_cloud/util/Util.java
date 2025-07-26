package com.example.viettel_cloud.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Base64;

public class Util {

    private static final String CODE_VERIFIER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
    private static final String ALPHA_NUMERIC_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int STATE_LENGTH = 10;
    
    public static <T> T stringToObject(Class<? extends T> type, String data) {
        if (data == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        try {
            return mapper.readValue(data, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String objectToString(Object data) {
        if (data == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String randomString(int count, String characters) {
        return RandomStringUtils.random(count, characters);
    }

    public static String randomString(int count) {
        return randomString(count, ALPHA_NUMERIC_CHARACTERS);
    }

    /**
     * Chuỗi chứa các ký tự A-Z, a-z, 0-9, và các ký tự dấu câu -._~
     * Độ dài từ 43 đến 128 ký tự.
     * @return Một chuỗi code_verifier được tạo ngẫu nhiên.
     */
    public static String generateCodeVerifier() {
        SecureRandom random = new SecureRandom();

        // random.nextInt(86) sẽ tạo ra một số từ 0 đến 85
        // Cộng với 43 sẽ cho kết quả trong khoảng [43, 128]
        int length = random.nextInt(86) + 43;
//        System.out.println("Generated code verifier length: " + length);

        return randomString(length, CODE_VERIFIER_CHARACTERS);
    }

    public static String genCodeChallenge(String codeVerifier) {
        byte[] hashVerifier = HmacSHA256.encrypt(codeVerifier);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hashVerifier);
    }
}
