package com.example.viettel_cloud.services.auth;

import com.example.viettel_cloud.dto.request.auth.LoginWithGidcReq;
import com.example.viettel_cloud.dto.response.auth.IAMLoginLinkRes;
import com.example.viettel_cloud.dto.response.auth.LoginRes;

public interface AuthService {
    IAMLoginLinkRes genViettelCloudIAMLoginLink(String redirectUri);

    LoginRes loginWithOIDC(LoginWithGidcReq request);
}
