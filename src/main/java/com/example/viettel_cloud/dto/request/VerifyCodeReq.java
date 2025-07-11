package com.example.viettel_cloud.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyCodeReq {
    private String state;
    private String error;
    private String code;
}
