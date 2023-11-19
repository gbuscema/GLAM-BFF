package com.glam.bff.configuration;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ToString
public class GlamServiceProperties {

    private String endpoint;

    @NestedConfigurationProperty
    private GlamSecurityProperties security = new GlamSecurityProperties();

    @Data
    public class GlamSecurityProperties {
        private boolean basicAuthentication = false;
        private String basicAuthUsername;
        private String basicAuthPassword;
    }
}