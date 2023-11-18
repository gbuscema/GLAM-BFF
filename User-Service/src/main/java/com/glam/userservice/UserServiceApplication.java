package com.glam.userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.glam.userservice.*")
@EnableConfigurationProperties
@ComponentScan(basePackages = { "com.glam.userservice.*" })
@EntityScan(basePackages = {"com.glam.userservice.dao"})
@SpringBootApplication(scanBasePackages = "com.glam.userservice", exclude = {LiquibaseAutoConfiguration.class})
public class UserServiceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}