package com.example.viettel_cloud.Service;

import com.example.viettel_cloud.dto.request.VerifyCodeReq;

public interface SubscriptionService {
    public void processSubscription(String whId, String whTimestamp, String whSignature, String rawBody);

    Object verifyCode(VerifyCodeReq request);
}
