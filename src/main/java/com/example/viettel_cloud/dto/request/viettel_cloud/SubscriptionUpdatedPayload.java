package com.example.viettel_cloud.dto.request.viettel_cloud;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionUpdatedPayload {
    @JsonProperty("old_subscription")
    private SubscriptionRecord oldSubscription;

    private SubscriptionRecord subscription;
}
