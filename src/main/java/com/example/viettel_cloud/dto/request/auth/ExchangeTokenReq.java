package com.example.viettel_cloud.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeTokenReq {
    private String code;

    @JsonProperty("code_verifier")
    private String codeVerifier;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("redirect_uri")
    private String redirectUri;

    @JsonProperty("grant_type")
    private String grantType = "authorization_code";

    public ExchangeTokenReq(String code, String codeVerifier, String clientId, String redirectUri) {
        this.code = code;
        this.codeVerifier = codeVerifier;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.grantType = "authorization_code";
    }
}
