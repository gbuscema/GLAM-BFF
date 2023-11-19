package com.glam.bff.client.user;

import com.glam.bff.configuration.ServiceConfigurationProperties;
import com.glam.bff.openapi.user.api.AuthenticationApi;
import com.glam.bff.openapi.user.model.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationClient extends AuthenticationApi {

    public AuthenticationClient(
            ServiceConfigurationProperties.GlamUserClientProperties userProperties) {
        super();

        super.getApiClient().setBasePath(userProperties.getEndpoint());
        /*super.getApiClient().setUsername(userProperties.getSecurity().getBasicAuthUsername());
        super.getApiClient().setPassword(userProperties.getSecurity().getBasicAuthPassword());*/
    }

    @Override
    public UserDAO registerUser(UserDAO userDAO) {
        return super.registerUser(userDAO);
    }
    @Override
    public UserDAO loginUser(UserDAO userDAO) {
        return super.loginUser(userDAO);
    }
    @Override
    public UserDAO getUserInfo(String userId) {
        return super.getUserInfo(userId);
    }
    @Override
    public UserDAO updateUserInfo(Integer userId, UserDAO userDAO) {
        return super.updateUserInfo(userId, userDAO);
    }

}
