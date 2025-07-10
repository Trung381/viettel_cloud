package com.example.viettel_cloud.dto.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface BaseEnum<T> {
    @JsonValue
    T toValue();
}
