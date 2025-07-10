package com.example.viettel_cloud.dto.response;


import com.example.viettel_cloud.dto.enums.EventType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViettelCloudCallback {
    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("event_type")
    private EventType eventType;
    @JsonProperty("payload")
    private Payload payload;

    @Getter
    @Setter
    public static class Payload {
        @JsonProperty("old_subscription")
        private SubscriptionRecord oldSubscription;
        private SubscriptionRecord subscription;
    }

}
