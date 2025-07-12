package com.example.viettel_cloud.other_service.viettel_cloud_iam;

import com.example.viettel_cloud.configuration.APIConnectionConfig;
import com.example.viettel_cloud.dto.request.ExchangeTokenReq;
import com.example.viettel_cloud.dto.response.ExchangeTokenRes;
import com.example.viettel_cloud.util.RetrofitCommunication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.example.viettel_cloud.util.Util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class ViettelCloudIAMServiceImpl implements ViettelCloudIAMService {
    private ViettelCloudIAMCommunicate communicate;
    private final APIConnectionConfig apiConnectionConfig;

    @Value("${viettel-cloud.iam.base-url}")
    private String baseUrl;

    @Value("${viettel-cloud.iam.client-id}")
    private String clientId;

    @Value("${viettel-cloud.iam.redirect-url}")
    private String redirectUri;

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

    @Override
    public String genLoginLink() {
        String state = genState();
        String codeVerifier = generateCodeVerifier();
        String codeChallenge = genCodeChallenge(codeVerifier);

        //TODO: Lưu codeVerifier để sử dụng sau khi nhận code (gửi lại để lấy access_token)
//        System.out.println("Code Verifier: " + codeVerifier);
//        System.out.println("Code Challenge: " + codeChallenge);
//        System.out.println("State: " + state);

        return baseUrl
                + "?protocol=oauth2"
                + "&response_type=code"
                + "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&scope=openid"
                + "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8)
                + "&code_challenge_method=S256"
                + "&code_challenge=" + URLEncoder.encode(codeChallenge, StandardCharsets.UTF_8);
    }
}
