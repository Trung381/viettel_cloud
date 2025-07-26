package com.example.viettel_cloud.services.auth;

import com.example.viettel_cloud.dto.StateAndCodeVerifier;
import com.example.viettel_cloud.dto.request.auth.ExchangeTokenReq;
import com.example.viettel_cloud.dto.request.auth.LoginWithGidcReq;
import com.example.viettel_cloud.dto.response.ExchangeTokenRes;
import com.example.viettel_cloud.dto.response.auth.IAMLoginLinkRes;
import com.example.viettel_cloud.dto.response.auth.IamUserIdentity;
import com.example.viettel_cloud.dto.response.auth.LoginRes;
import com.example.viettel_cloud.entities.User;
import com.example.viettel_cloud.other_service.viettel_cloud_iam.ViettelCloudIAMService;
import com.example.viettel_cloud.security.JwtTokenProvider;
import com.example.viettel_cloud.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ViettelCloudIAMService viettelCloudIAMService;

    @Value("${viettel-cloud.iam.client-id}")
    private String clientId;

    @Value("${connection.config.viettel-cloud-iam.api-url}")
    private String iamApiUrl;

    @Value("${app.secret-key}")
    private String secretKey;

    @Value("${app.jwtAdminExpirationInMs}")
    private long jwtAdminExpirationInMs;

    @Override
    public IAMLoginLinkRes genViettelCloudIAMLoginLink(String redirectUri) {
        String state = Util.randomString(10);

        String codeVerifier = Util.generateCodeVerifier();
        String codeChallenge = Util.genCodeChallenge(codeVerifier);

        String link = iamApiUrl + "/realms/viettel-cloud/protocol/openid-connect/auth"
                + "?protocol=oauth2"
                + "&response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=openid"
                + "&state=" + state
                + "code_challenge_method=S256"
                + "&code_challenge=" + codeChallenge;

        StateAndCodeVerifier verifier = new StateAndCodeVerifier(state, codeVerifier);
        String tokenVerifier = jwtTokenProvider.genTokenHS512(Util.objectToString(verifier), secretKey, 180000); //3 phút

        return new IAMLoginLinkRes(link, tokenVerifier);
    }

    public LoginRes loginWithOIDC(LoginWithGidcReq request) {
        if (request.getError() != null) {
            throw new RuntimeException("403 FORBIDDEN");
        }

        StateAndCodeVerifier verifier = Util.stringToObject(
                StateAndCodeVerifier.class,
                jwtTokenProvider.getSubIdFromTokenHS512(request.getTokenVerifier(), secretKey)
        );

        if (verifier == null || !request.getState().equals(verifier.getState())) {
            throw new RuntimeException("404 NOT FOUND");
        }

        ExchangeTokenReq exchangeTokenRequest = new ExchangeTokenReq(
                request.getCode(),
                verifier.getCodeVerifier(),
                clientId,
                request.getRedirectUri()
        );

        ExchangeTokenRes exchangeTokenResponse = viettelCloudIAMService.exchangeToken(exchangeTokenRequest);
        //todo: Hiện tại chưa verify id_token
        IamUserIdentity iamUserIdentity = Util.stringToObject(IamUserIdentity.class, jwtTokenProvider.getPayload(exchangeTokenResponse.getIdToken()));

        //todo: Nếu đăng nhập lần đầu => Tạo user mới trong DB liên kết đến identity
        //todo: Nếu đã có tài khoản => Truy vấn thông tin, gen token của hệ thống và trả về response

        return new LoginRes();
    }

    private User createUser(IamUserIdentity iamUserIdentity) {
        return null;
    }

    private User getUser(String sub, String iss) {
        return null;
    }
}
