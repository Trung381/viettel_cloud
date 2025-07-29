package com.example.viettel_cloud.services.viettel_cloud.billing;

import com.example.viettel_cloud.dto.enums.EventType;
import com.example.viettel_cloud.dto.request.viettel_cloud.SubscriptionRecord;
import com.example.viettel_cloud.dto.request.viettel_cloud.SubscriptionUpdatedPayload;
import com.example.viettel_cloud.dto.request.viettel_cloud.ViettelCloudCallbackReq;
import com.example.viettel_cloud.exception.BusinessException;
import com.example.viettel_cloud.util.HmacSHA256;
import com.example.viettel_cloud.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ViettelCloudBillingServiceImpl implements ViettelCloudBillingService {

    @Value("${viettel-cloud.webhook.secret}")
    private String whSecret;

    @Override
    public void handleCallback(String whId, Long whTimestamp, String whSignature, ViettelCloudCallbackReq request) {
        verifyRequest(whId, whTimestamp, whSignature, Util.objectToString(request));
        processCallbackAsync(request);
    }

    void verifyRequest(String whId, Long whTimestamp, String whSignature, String body) {
        try {
            if (HmacSHA256.verifyMessage(whSecret, whId, whTimestamp, whSignature, body)) {
                return;
            }
        } catch (Exception e) {
            log.error(e);
        }
        throw new BusinessException("Verify message failed!", HttpStatus.FORBIDDEN);
    }

    @Async
    void processCallbackAsync(ViettelCloudCallbackReq request) {
        EventType eventType = request.getEventType();
        switch (eventType) {
            case CREATED, EXPIRED, RENEWED, CANCELLED -> {
                SubscriptionRecord payload = (SubscriptionRecord) request.getPayload();
            }
            case UPDATED -> {
                SubscriptionUpdatedPayload payload = (SubscriptionUpdatedPayload) request.getPayload();
            }
            default -> {}
        }
    }
}
