package com.example.viettel_cloud.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginWithGidcReq {
    @NotBlank
    private String state;

    @NotBlank
    private String code;

    private String error;

    @NotBlank
    @JsonProperty("token_verifier")
    private String tokenVerifier;

    @NotBlank
    @JsonProperty("redirect_uri")
    private String redirectUri;
}
