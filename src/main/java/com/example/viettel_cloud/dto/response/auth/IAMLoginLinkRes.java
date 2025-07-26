package com.example.viettel_cloud.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IAMLoginLinkRes {
    private String link;
    @JsonProperty("token_verifier")
    private String tokenVerifier;
}
