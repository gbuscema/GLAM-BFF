package com.glam.wardrobeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = "com.glam.wardrobeservice", exclude = {LiquibaseAutoConfiguration.class})//, SecurityAutoConfiguration.class})
public class WardrobeServiceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(WardrobeServiceApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(WardrobeServiceApplication.class, args);
    }
}