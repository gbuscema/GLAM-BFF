package com.glam.bff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties
@ComponentScan(basePackages = { "com.glam.bff.*" })
@SpringBootApplication(scanBasePackages = "com.glam.bff", exclude = {LiquibaseAutoConfiguration.class})//, SecurityAutoConfiguration.class})
public class GlamBffApplication {

	private static final Logger LOG = LoggerFactory.getLogger(GlamBffApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GlamBffApplication.class, args);
	}
}
