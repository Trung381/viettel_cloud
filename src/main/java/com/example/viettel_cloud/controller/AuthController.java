package com.example.viettel_cloud.controller;

import com.example.viettel_cloud.dto.request.auth.LoginWithOidcReq;
import com.example.viettel_cloud.services.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("v1/auth/get-iam-login-link")
    public ResponseEntity<?> getIamLoginLink(@RequestParam("redirect-uri") String redirectUri) {
        return ResponseEntity.ok(authService.genViettelCloudIAMLoginLink(redirectUri));
    }

    @PostMapping("v1/auth/oidc-login")
    public ResponseEntity<?> loginWithOIDC(@RequestBody @Valid LoginWithOidcReq request) {
        return ResponseEntity.ok(authService.loginWithOIDC(request));
    }
}
