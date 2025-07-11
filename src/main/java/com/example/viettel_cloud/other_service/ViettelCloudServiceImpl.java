package com.example.viettel_cloud.other_service;

import com.example.viettel_cloud.configuration.APIConnectionConfig;
import com.example.viettel_cloud.dto.response.SubscriptionRecord;
import com.example.viettel_cloud.util.RetrofitCommunication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class ViettelCloudServiceImpl implements ViettelCloudService {
    private ViettelCloudCommunicate communicate;
    private final APIConnectionConfig apiConnectionConfig;

    @PostConstruct
    void init() {
        communicate = RetrofitCommunication.buildSetting(ViettelCloudCommunicate.class, apiConnectionConfig.getViettelCloud().getApiUrl(), apiConnectionConfig.getViettelCloud());
    }

    @Override
    public List<SubscriptionRecord> getSubscriptions(String apiKey, String customerId, List<String> subscriptionFilters, String planId, String status) {
        Call<List<SubscriptionRecord>> call = communicate.getSubscriptions(apiKey, customerId, subscriptionFilters, planId, status);
        return handleResponse(call);
    }

    @Override
    public SubscriptionRecord getSubscriptionDetail(String apiKey, String subscriptionId) {
        Call<SubscriptionRecord> call = communicate.getSubscriptionDetail(apiKey, subscriptionId);
        return handleResponse(call);
    }

    @Override
    public SubscriptionRecord updateSubscription(String apiKey, String subscriptionId, Map<String, Object> metadata) {
        Call<SubscriptionRecord> call = communicate.updateSubscription(apiKey, subscriptionId, metadata);
        return handleResponse(call);
    }

    @Override
    public String initLoginLink(String baseUrl, String clientId, String redirectUri) {
        return "";
    }

    private <T> T handleResponse(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                try (ResponseBody body = response.errorBody()) {
                    String errBody = body == null ? "" : body.string();
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(errBody);

                    Object data = objectMapper.convertValue(jsonNode, Object.class);

                    log.error("Process check code = {}, message={}, response={}", response.code(), response.message(), errBody);

//                    throw new BusinessException(jsonNode, mess, HttpStatus.BAD_REQUEST);
                    throw new RuntimeException();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
