package com.example.viettel_cloud.other_service.viettel_cloud_iam;

import com.example.viettel_cloud.dto.request.ExchangeTokenReq;
import com.example.viettel_cloud.dto.response.ExchangeTokenRes;

public interface ViettelCloudIAMService {
    ExchangeTokenRes exchangeToken(ExchangeTokenReq request);
    String genLoginLink();
}
