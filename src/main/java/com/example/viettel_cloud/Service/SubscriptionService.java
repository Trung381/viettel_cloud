package com.example.viettel_cloud.Service;

public interface SubscriptionService {
    public void processSubscription(String webhookId, String webhookTimestamp, String providedSignature, String rawBody);
}
