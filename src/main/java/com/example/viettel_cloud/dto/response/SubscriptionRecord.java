package com.example.viettel_cloud.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionRecord {

    @JsonProperty("addons")
    private List<AddonSubscription> addons;

    @JsonProperty("auto_renew")
    private boolean autoRenew;

    @JsonProperty("billing_plan")
    private BillingPlan billingPlan;

    @JsonProperty("customer")
    private Customer customer;

    @JsonProperty("end_date")
    private OffsetDateTime endDate;

    @JsonProperty("fully_billed")
    private boolean fullyBilled;

    @JsonProperty("is_new")
    private boolean isNew;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @JsonProperty("start_date")
    private OffsetDateTime startDate;

    @JsonProperty("subscription_filters")
    private List<Object> subscriptionFilters;

    @JsonProperty("subscription_id")
    private String subscriptionId;

}
