package com.example.viettel_cloud.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public final class HmacSHA256 {

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static byte[] encrypt(String message, String secretKey) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            return mac.doFinal(message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC", e);
        }
    }

    public static byte[] encrypt(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HMAC_SHA256_ALGORITHM);
            return digest.digest(message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC", e);
        }
    }

    public static boolean verifyMessage(String secretKey, String whTimestamp, String whId, String whSignature, String requestBody, long expires) throws Exception {
        long whTime = Long.parseLong(whTimestamp);
        long currentTime = System.currentTimeMillis();
        if (currentTime - whTime * 1000 > expires) { // 5 phút = 300000 ms
            return false;
        }

        String dataToSign = whId + "." + whTimestamp + "." + requestBody;

        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        byte[] decodedSecret = Base64.getDecoder().decode(secretKey.substring(6)); //Bỏ whsec_
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodedSecret, HMAC_SHA256_ALGORITHM);
        mac.init(secretKeySpec);
        byte[] computedHmac = mac.doFinal(dataToSign.getBytes(StandardCharsets.UTF_8));
        String computedSignature = "v1," + Base64.getEncoder().encodeToString(computedHmac);

        return computedSignature.equals(whSignature);
    }

}
