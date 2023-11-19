package com.glam.bff.client.wardrobe;

import com.glam.bff.configuration.ServiceConfigurationProperties;
import com.glam.bff.openapi.wardrobe.api.WardrobeApi;
import com.glam.bff.openapi.wardrobe.api.WardrobeDbCreationApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MockClient extends WardrobeDbCreationApi {

    public MockClient(
            ServiceConfigurationProperties.GlamWardrobeClientProperties userProperties) {
        super();

        super.getApiClient().setBasePath(userProperties.getEndpoint());
        /*super.getApiClient().setUsername(userProperties.getSecurity().getBasicAuthUsername());
        super.getApiClient().setPassword(userProperties.getSecurity().getBasicAuthPassword());*/
    }

}
