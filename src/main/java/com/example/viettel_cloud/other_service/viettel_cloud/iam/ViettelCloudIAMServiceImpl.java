package com.example.viettel_cloud.other_service.viettel_cloud.iam;

import com.example.viettel_cloud.configuration.APIConnectionConfig;
import com.example.viettel_cloud.dto.request.auth.ExchangeTokenReq;
import com.example.viettel_cloud.dto.response.auth.ExchangeTokenRes;
import com.example.viettel_cloud.util.RetrofitCommunication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Log4j2
@Service
@RequiredArgsConstructor
public class ViettelCloudIAMServiceImpl implements ViettelCloudIAMService {
    private ViettelCloudIAMCommunicate communicate;
    private final APIConnectionConfig apiConnectionConfig;

    @PostConstruct
    void init() {
        communicate = RetrofitCommunication.buildSetting(ViettelCloudIAMCommunicate.class, apiConnectionConfig.getViettelCloudIAM().getApiUrl(), apiConnectionConfig.getViettelCloudIAM());
    }

    @Override
    public ExchangeTokenRes exchangeToken(ExchangeTokenReq request) {
        Call<ExchangeTokenRes> call = communicate.exchangeToken(request);
        return handleResponse(call);
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
