package com.example.viettel_cloud.Controller;

import com.example.viettel_cloud.Service.SubscriptionService;
import com.example.viettel_cloud.dto.request.VerifyCodeReq;
import com.example.viettel_cloud.dto.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("v1/subscription/wh")
    public ResponseEntity<String> ex(@RequestHeader("Webhook-Id") String webhookId,
                                     @RequestHeader("Webhook-Timestamp") String webhookTimestamp,
                                     @RequestHeader("Webhook-Signature") String whSignature,
                                     @RequestBody String body) {
//        return ResponseEntity.ok(service.ex(request));
        //TODO
        return ResponseEntity.ok(null);
    }

    @PostMapping("v1/verify-code")
    public ResponseEntity<BaseResponse<?>> verifyCode(VerifyCodeReq request) {
        return ResponseEntity.ok(new BaseResponse<>(subscriptionService.verifyCode(request)));
    }

}
