package com.example.viettel_cloud.dto.response.auth;

import com.example.viettel_cloud.entities.User;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRes {
    @JsonUnwrapped
    private User user;
    private String token;
}
