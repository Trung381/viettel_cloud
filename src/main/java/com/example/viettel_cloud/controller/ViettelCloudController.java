package com.example.viettel_cloud.controller;

import com.example.viettel_cloud.dto.request.viettel_cloud.ViettelCloudCallbackReq;
import com.example.viettel_cloud.dto.response.BaseResponse;
import com.example.viettel_cloud.services.viettel_cloud.billing.ViettelCloudBillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ViettelCloudController {
    private final ViettelCloudBillingService viettelCloudBillingService;

    //todo: hidden
    @PostMapping("v1/viettel-cloud/webhook")
    public ResponseEntity<?> webhook(
            @RequestHeader("Webhook-Id") String whId,
            @RequestHeader("Webhook-Timestamp") Long whTimestamp,
            @RequestHeader("Webhook-Signature") String whSignature,
            @RequestBody ViettelCloudCallbackReq request
    ) {
        viettelCloudBillingService.handleCallback(whId, whTimestamp, whSignature, request);
        return ResponseEntity.ok(new BaseResponse<>());
    }
}
