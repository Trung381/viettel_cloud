package com.example.viettel_cloud.services.auth;

import com.example.viettel_cloud.dto.request.auth.StateAndCodeVerifier;
import com.example.viettel_cloud.dto.request.auth.ExchangeTokenReq;
import com.example.viettel_cloud.dto.request.auth.LoginWithOidcReq;
import com.example.viettel_cloud.dto.response.auth.ExchangeTokenRes;
import com.example.viettel_cloud.dto.response.auth.IAMLoginLinkRes;
import com.example.viettel_cloud.dto.response.auth.IamUserIdentity;
import com.example.viettel_cloud.dto.response.auth.LoginRes;
import com.example.viettel_cloud.entities.User;
import com.example.viettel_cloud.other_service.viettel_cloud.iam.ViettelCloudIAMService;
import com.example.viettel_cloud.repositories.user.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

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

    public LoginRes loginWithOIDC(LoginWithOidcReq request) {
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

        User user = userRepository.getUserByIssAndSub(iamUserIdentity.getIss(), iamUserIdentity.getSub());

        //Nếu đăng nhập lần đầu => Tạo user mới trong DB liên kết đến identity
        if (user == null) {
            user = createUser(iamUserIdentity);
        }

        LoginRes response = getLoginRes(user);
        response.setToken(jwtTokenProvider.genTokenHS512(String.valueOf(user.getId()), secretKey, jwtAdminExpirationInMs));
        return response;
    }

    private User createUser(IamUserIdentity iamUserIdentity) {
        User user = new User();
        user.setEmail(iamUserIdentity.getEmail());
        user.setName(iamUserIdentity.getName());
        user.setIss(iamUserIdentity.getIss());
        user.setSub(iamUserIdentity.getSub());
        //todo: Thông tin khác nếu có

        return userRepository.save(user);
    }

    private LoginRes getLoginRes(User user) {
        LoginRes loginResponse = new LoginRes();
        loginResponse.setUser(user);

        return loginResponse;
    }
}
