package com.example.viettel_cloud.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingPlan {

    @JsonProperty("plan_id")
    private String planId;

    @JsonProperty("plan_name")
    private String planName;

    @JsonProperty("version")
    private int version;

    @JsonProperty("version_id")
    private String versionId;

}