package com.example.viettel_cloud.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class Util {

    private static final String CODE_VERIFIER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
    private static final String STATE_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
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

    public static String randomString(int count, String characters) {
        return RandomStringUtils.random(count, characters);
    }

    public static String genState() {
        return randomString(STATE_LENGTH, STATE_CHARACTERS);
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
        System.out.println("Generated code verifier length: " + length);

        return randomString(length, CODE_VERIFIER_CHARACTERS);
    }

    /**
     * <p>code challenge = Base64-URL-encoding( Base64-encoding( SHA256( code verifier)))</p>
     * <p>Thứ tự thực hiện như sau:</p>
     * <ol>
     * <li>{@code generateCodeVerifier()} &rarr; tạo ra chuỗi ngẫu nhiên.</li>
     * <li>{@code genCodeChallenge(String input)} &rarr; mã hóa Base64-URL kết quả băm.</li>
     * </ol>
     * <b>Lưu ý:</b> Kết quả của hàm trước là đầu vào cho hàm sau.
     * @return Chuỗi {@code code_challenge} cuối cùng để sử dụng trong API đăng nhập.
     */
    public static String genCodeChallenge(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Chuyển chuỗi đầu vào thành mảng byte
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));

            // Chuyển mảng byte thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0'); // thêm 0 nếu thiếu
                hexString.append(hex);
            }

            return Base64.getUrlEncoder().withoutPadding().encodeToString(hexString.toString().getBytes());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
