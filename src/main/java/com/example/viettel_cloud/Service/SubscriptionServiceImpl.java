package com.example.viettel_cloud.Service;

import com.example.viettel_cloud.dto.response.ViettelCloudCallback;
import com.example.viettel_cloud.exception.WebhookVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final String webhookSecret;
    private final ObjectMapper objectMapper;
    final long FIVE_MINUTES_IN_SECONDS = 5 * 60;

    public SubscriptionServiceImpl(@Value("${viettel.webhook.secret}") String webhookSecret) {
        this.webhookSecret = webhookSecret;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }


    @Override
    public void processSubscription(String webhookId, String webhookTimestamp, String providedSignature, String rawBody) {
        verifyTimestamp(webhookTimestamp);

        verifySignature(webhookId, webhookTimestamp, rawBody, providedSignature);

        try {
            ViettelCloudCallback event = objectMapper.readValue(rawBody, ViettelCloudCallback.class);

            // TODO:
            System.out.println("Event Type: " + event.getEventType());
            // ...

        } catch (Exception e) {
            // Nếu parse lỗi, ném ra exception
            throw new WebhookVerificationException("Invalid JSON body format: " + e.getMessage());
        }
    }

    private void verifyTimestamp(String webhookTimestamp) {
        long requestTime;
        try {
            requestTime = Long.parseLong(webhookTimestamp);
        } catch (NumberFormatException e) {
            throw new WebhookVerificationException("Invalid timestamp format.");
        }

        long currentTime = Instant.now().getEpochSecond();

        if (currentTime - requestTime > FIVE_MINUTES_IN_SECONDS) {
            throw new WebhookVerificationException("Webhook request is too old.");
        }
    }

    private void verifySignature(String webhookId, String webhookTimestamp, String rawBody, String providedSignature) {
        try {
            String signingString = webhookId + "." + webhookTimestamp + "." + rawBody;

            String calculatedSignature = calculateSignature(signingString);

            if (!MessageDigest.isEqual(calculatedSignature.getBytes(StandardCharsets.UTF_8), providedSignature.getBytes(StandardCharsets.UTF_8))) {
                throw new WebhookVerificationException("Webhook signature does not match.");
            }

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Could not validate signature due to internal server error.", e);
        }
    }

    private String calculateSignature(String data) throws NoSuchAlgorithmException, InvalidKeyException {
        // **Quan trọng**: Bỏ tiền tố "whsec_" khỏi secret
        String keyWithoutPrefix = webhookSecret.replace("whsec_", "");

        byte[] secretKeyBytes = Base64.getDecoder().decode(keyWithoutPrefix);

        // Tạo HMAC-SHA256
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
        sha256Hmac.init(secretKeySpec);

        // Ký dữ liệu
        byte[] signatureBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // **Quan trọng**: Mã hóa kết quả bằng Base64 và thêm tiền tố "v1,"
        return "v1," + Base64.getEncoder().encodeToString(signatureBytes);
    }
}
