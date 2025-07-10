package com.example.viettel_cloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Exception này sẽ tự động trả về HTTP 400 Bad Request khi được ném ra từ Controller
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WebhookVerificationException extends RuntimeException {
    public WebhookVerificationException(String message) {
        super(message);
    }
}
