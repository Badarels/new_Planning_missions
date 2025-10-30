package com.businesscenterservices.missions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MissionsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MissionsServiceApplication.class, args);
    }
}
