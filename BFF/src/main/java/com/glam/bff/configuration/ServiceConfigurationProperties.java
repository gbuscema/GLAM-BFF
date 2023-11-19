package com.glam.bff.configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfigurationProperties {

	@Data
	@Configuration
	@ConfigurationProperties(prefix = "service.glam.services.user")
	@EqualsAndHashCode(callSuper = true)
	public class GlamUserClientProperties extends GlamServiceProperties {}


}
