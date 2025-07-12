package com.example.viettel_cloud.Service;

import com.example.viettel_cloud.dto.request.ExchangeTokenReq;
import com.example.viettel_cloud.dto.request.VerifyCodeReq;
import com.example.viettel_cloud.dto.response.ExchangeTokenRes;
import com.example.viettel_cloud.dto.response.ViettelCloudCallback;
import com.example.viettel_cloud.exception.WebhookVerificationException;
import com.example.viettel_cloud.other_service.viettel_cloud_iam.ViettelCloudIAMService;
import com.example.viettel_cloud.security.JwtTokenProvider;
import com.example.viettel_cloud.util.HmacSHA256;
import com.example.viettel_cloud.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
//@SessionAttributes("state")
public class SubscriptionServiceImpl implements SubscriptionService {

    @Value("${viettel.webhook.secret}")
    private String webhookSecret;
    final long FIVE_MINUTES_IN_MILLISECONDS = 5 * 60 * 1000;
    private final ViettelCloudIAMService iamService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${viettel-cloud.iam.client-id}")
    private String clientId;

    @Value("${viettel-cloud.iam.redirect-url}")
    private String redirectUrl;

    @Override
    public void processSubscription(String whId, String whTimestamp, String whSignature, String rawBody) {
//        verifyTimestamp(webhookTimestamp);
//
//        verifySignature(webhookId, webhookTimestamp, rawBody, providedSignature);

        try {
            if (!HmacSHA256.verifyMessage(webhookSecret, whTimestamp, whId, whSignature, rawBody, FIVE_MINUTES_IN_MILLISECONDS)) {
                //todo: ignore request
                return;
            }

            ViettelCloudCallback request = Util.stringToObject(ViettelCloudCallback.class, rawBody);
            if (request == null) {
                //todo: xử lý khi parse thất bại
                return;
            }

            // TODO: Logic xử lý khi nhận được callback
            System.out.println("Event Type: " + request.getEventType());
            // ...

        } catch (Exception e) {
            // Nếu parse lỗi, ném ra exception
            throw new WebhookVerificationException("Invalid JSON body format: " + e.getMessage());
        }
    }

    @Override
    public Object verifyCode(VerifyCodeReq request) {
        // Giả sử xác thực code thành công
        // => Gọi exchange token
        ExchangeTokenReq exchangeTokenReq = new ExchangeTokenReq();
        exchangeTokenReq.setCode(request.getCode());
        exchangeTokenReq.setCodeVerifier("");
        exchangeTokenReq.setClientId(clientId);
        exchangeTokenReq.setRedirectUri(redirectUrl);

        ExchangeTokenRes exchangeTokenRes = iamService.exchangeToken(exchangeTokenReq);
        return jwtTokenProvider.getUserIdentity(exchangeTokenRes.getAccessToken());
    }
}
