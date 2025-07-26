package com.example.viettel_cloud.services;

public interface SubscriptionService {
    void processSubscription(String whId, String whTimestamp, String whSignature, String rawBody);
}
