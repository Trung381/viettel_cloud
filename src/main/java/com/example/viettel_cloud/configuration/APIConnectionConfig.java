package com.example.viettel_cloud.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "connection.config")
public class APIConnectionConfig {

    private ViettelCloud viettelCloud;
    private ViettelCloudIAM viettelCloudIAM;

    @Getter
    @Setter
    public static class Connection {
        private String apiUrl;
        private int maxRequest = 200;
        private int maxRequestPerHost = 50;
        private long connectTimeout = 60;
    }

    @Getter
    @Setter
    public static class ViettelCloud extends Connection {
        private String apiKey;
    }

    @Getter
    @Setter
    public static class ViettelCloudIAM extends Connection {
    }
}
