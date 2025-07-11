package com.example.viettel_cloud.other_service;

import com.example.viettel_cloud.dto.response.SubscriptionRecord;

import java.util.List;
import java.util.Map;

public interface ViettelCloudService {
    List<SubscriptionRecord> getSubscriptions(String apiKey, String customerId, List<String> subscriptionFilters, String planId, String status);

    SubscriptionRecord getSubscriptionDetail(String apiKey, String subscriptionId);

    SubscriptionRecord updateSubscription(String apiKey, String subscriptionId, Map<String, Object> metadata);
}
