package com.glam.bff.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glam.bff.client.machinelearning.KNNClient;
import com.glam.bff.client.openai.ChatCompletionClient;
import com.glam.bff.client.user.AuthenticationClient;
import com.glam.bff.dto.authentication.UserInfoDTO;
import com.glam.bff.dto.authentication.UserLoginDTO;
import com.glam.bff.dto.authentication.UserLoginResponseDTO;
import com.glam.bff.dto.authentication.UserRegistrationDTO;
import com.glam.bff.mapper.user.UserDTOMapper;
import com.glam.bff.mapper.wardrobe.GarmentDTOMapper;
import com.glam.bff.openapi.user.model.UserDAO;
import com.glam.bff.repository.UserWardrobeRepository;
import com.glam.bff.utils.WardrobeVectorStoreBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ChatCompletionClient chatCompletionClient;

    @Autowired
    private KNNClient knnClient;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WardrobeVectorStoreBuilder wardrobeVectorStoreBuilder;

    @Autowired
    private UserWardrobeRepository userWardrobeRepository;

    @Autowired
    private AuthenticationClient authenticationClient;

    @Autowired
    private GarmentDTOMapper garmentDTOMapper;

    public UserLoginResponseDTO registerUser(UserRegistrationDTO body) {

        // DTO -> DAO
        UserDTOMapper userDTOMapper = applicationContext.getBean(UserDTOMapper.class);
        UserDAO userDAO = userDTOMapper.registrationDtoToDao(body);

        UserDAO registeredUser = authenticationClient.registerUser(userDAO);

        return userDTOMapper.loginDaoToDto(registeredUser);

    }

    public UserLoginResponseDTO loginUser(UserLoginDTO body) throws Exception {

        UserDTOMapper userDTOMapper = applicationContext.getBean(UserDTOMapper.class);
        UserDAO userDAO = userDTOMapper.loginDtoToDao(body);

        UserDAO loggedUserDAO = authenticationClient
                .loginUser(userDAO);

        // DTO -> DAO
        return userDTOMapper.loginDaoToDto(loggedUserDAO);

    }

    public UserInfoDTO getUserInfo(String userId) throws Exception {

        UserDAO userDAO = authenticationClient.getUserInfo(userId);

        // DAO -> DTO
        UserDTOMapper userDTOMapper = applicationContext.getBean(UserDTOMapper.class);
        return userDTOMapper.userInfoDaoToDto(userDAO);

    }

    public UserInfoDTO updateUserInfo(int userId, UserRegistrationDTO body) throws Exception {

        // DTO -> DAO
        UserDTOMapper userDTOMapper = applicationContext.getBean(UserDTOMapper.class);
        UserDAO userDAO = userDTOMapper.registrationDtoToDao(body);

        UserDAO savedUserDAO = authenticationClient.updateUserInfo(userId, userDAO);

        return userDTOMapper.userInfoDaoToDto(savedUserDAO);

    }
}
