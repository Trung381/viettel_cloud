package com.example.viettel_cloud.Service;

public interface SubscriptionService {
    public void processSubscription(String whId, String whTimestamp, String whSignature, String rawBody);
}
