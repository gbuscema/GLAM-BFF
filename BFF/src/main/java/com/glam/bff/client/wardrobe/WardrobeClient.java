package com.glam.bff.client.wardrobe;

import com.glam.bff.configuration.ServiceConfigurationProperties;
import com.glam.bff.openapi.user.api.AuthenticationApi;
import com.glam.bff.openapi.user.model.UserDAO;
import com.glam.bff.openapi.wardrobe.api.WardrobeApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WardrobeClient extends WardrobeApi {

    public WardrobeClient(
            ServiceConfigurationProperties.GlamWardrobeClientProperties userProperties) {
        super();

        super.getApiClient().setBasePath(userProperties.getEndpoint());
        /*super.getApiClient().setUsername(userProperties.getSecurity().getBasicAuthUsername());
        super.getApiClient().setPassword(userProperties.getSecurity().getBasicAuthPassword());*/
    }

}
