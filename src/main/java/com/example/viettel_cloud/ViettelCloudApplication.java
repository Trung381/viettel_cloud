package com.example.viettel_cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ViettelCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViettelCloudApplication.class, args);
    }

}
