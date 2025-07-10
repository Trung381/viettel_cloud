package com.example.viettel_cloud.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddonSubscription {

    @JsonProperty("addon")
    private Addon addon;

    @JsonProperty("addon_subscription_id")
    private String addonSubscriptionId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("end_date")
    private OffsetDateTime endDate;

    @JsonProperty("fully_billed")
    private boolean fullyBilled;

    @JsonProperty("start_date")
    private OffsetDateTime startDate;

}
