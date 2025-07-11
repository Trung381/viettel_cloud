package com.example.viettel_cloud.other_service.viettel_cloud;

import com.example.viettel_cloud.dto.response.SubscriptionRecord;

import java.util.List;
import java.util.Map;

public interface ViettelCloudService {
    List<SubscriptionRecord> getSubscriptions(String customerId, List<String> subscriptionFilters, String planId, String status);

    SubscriptionRecord getSubscriptionDetail(String subscriptionId);

    SubscriptionRecord updateSubscription(String subscriptionId, Map<String, Object> metadata);
}
