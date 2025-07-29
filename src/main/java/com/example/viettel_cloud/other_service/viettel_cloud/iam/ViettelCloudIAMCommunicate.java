package com.example.viettel_cloud.other_service.viettel_cloud.iam;

import com.example.viettel_cloud.dto.request.auth.ExchangeTokenReq;
import com.example.viettel_cloud.dto.response.auth.ExchangeTokenRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ViettelCloudIAMCommunicate {
    @POST("/realms/viettel-cloud/protocol/openid-connect/token")
    Call<ExchangeTokenRes> exchangeToken(@Body ExchangeTokenReq body);
}
