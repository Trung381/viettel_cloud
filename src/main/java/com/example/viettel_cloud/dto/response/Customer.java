package com.example.viettel_cloud.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("email")
    private String email;

}
