package com.example.viettel_cloud.dto.request.viettel_cloud;

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

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddonSubscription {
        private Addon addon;

        @JsonProperty("addon_subscription_id")
        private String subscriptionId;

        private int quantity;

        @JsonProperty("end_date")
        private OffsetDateTime endDate;

        @JsonProperty("start_date")
        private OffsetDateTime startDate;

        @JsonProperty("fully_billed")
        private boolean fullyBilled;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Addon {
        @JsonProperty("addon_id")
        private String id;

        @JsonProperty("addon_name")
        private String name;

        @JsonProperty("addon_type")
        private String type;

        @JsonProperty("billing_frequency")
        private String billingFrequency;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BillingPlan {
        @JsonProperty("plan_id")
        private String id;

        @JsonProperty("plan_name")
        private String name;

        private int version;

        @JsonProperty("version_id")
        private String versionId;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Customer {
        @JsonProperty("customer_id")
        private String id;

        @JsonProperty("customer_name")
        private String name;

        private String email;
    }
}
