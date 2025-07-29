package com.example.viettel_cloud.services.viettel_cloud.billing;

import com.example.viettel_cloud.dto.request.viettel_cloud.ViettelCloudCallbackReq;

public interface ViettelCloudBillingService {
    void handleCallback(String whId, Long whTimestamp, String whSignature, ViettelCloudCallbackReq request);
}
