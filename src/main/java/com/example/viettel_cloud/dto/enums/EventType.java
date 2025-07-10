package com.example.viettel_cloud.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
public enum EventType implements BaseEnum<String> {

    CREATED("subscription.created"),
    UPDATED("subscription.updated"),
    EXPIRED("subscription.expired"),
    RENEWED("subscription.renewed"),
    CANCELLED("subscription.cancelled");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    @Override
    public String toValue() {
        return value;
    }

    @JsonCreator
    public static EventType fromValue(String value) {
        for (EventType val : values()) {
            if (val.value.equalsIgnoreCase(value)) {
                return val;
            }
        }
        return null;
    }

}
