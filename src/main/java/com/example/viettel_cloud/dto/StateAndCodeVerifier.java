package com.example.viettel_cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateAndCodeVerifier {
    private String state;
    private String codeVerifier;
}
