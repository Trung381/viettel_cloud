package com.example.viettel_cloud.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Addon {

    @JsonProperty("addon_id")
    private String addonId;

    @JsonProperty("addon_name")
    private String addonName;

    @JsonProperty("addon_type")
    private String addonType;

    @JsonProperty("billing_frequency")
    private String billingFrequency;
}
