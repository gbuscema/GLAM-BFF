package com.glam.bff.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glam.bff.client.machinelearning.KNNClient;
import com.glam.bff.client.openai.ChatCompletionClient;
import com.glam.bff.dao.UserDAO;
import com.glam.bff.dto.authentication.UserInfoDTO;
import com.glam.bff.dto.authentication.UserLoginDTO;
import com.glam.bff.dto.authentication.UserLoginResponseDTO;
import com.glam.bff.dto.authentication.UserRegistrationDTO;
import com.glam.bff.mapper.user.UserDTOMapper;
import com.glam.bff.mapper.wardrobe.GarmentDTOMapper;
import com.glam.bff.repository.UserRepository;
import com.glam.bff.repository.UserWardrobeRepository;
import com.glam.bff.utils.WardrobeVectorStoreBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private UserRepository userRepository;

    @Autowired
    private GarmentDTOMapper garmentDTOMapper;

    public void registerUser(UserRegistrationDTO body) {

        // DTO -> DAO
        UserDTOMapper userDTOMapper = applicationContext.getBean(UserDTOMapper.class);
        UserDAO userDAO =userDTOMapper.registrationDtoToDao(body);

        userRepository.save(userDAO);

    }

    public UserLoginResponseDTO loginUser(UserLoginDTO body) throws Exception {

        UserDTOMapper userDTOMapper = applicationContext.getBean(UserDTOMapper.class);

        List<UserDAO> userDAOList = userRepository
                .findUsersByEmailAndPassword(body.getEmail(), body.getPassword());

        if(userDAOList == null || userDAOList.isEmpty()){
            throw new Exception("Wrong Email or Password!");
        }

        UserDAO userDAO = userDAOList.get(0);

        // DTO -> DAO
        return userDTOMapper.loginDaoToDto(userDAO);

    }

    public UserInfoDTO getUserInfo(String userId) throws Exception {

        Optional<UserDAO> userDAOOptional = userRepository.findById(userId);
        if(userDAOOptional.isEmpty()){
            throw new Exception("No User found for ID: " + userId);
        }

        UserDAO userDAO = userDAOOptional.get();

        // DAO -> DTO
        UserDTOMapper userDTOMapper = applicationContext.getBean(UserDTOMapper.class);
        return userDTOMapper.userInfoDaoToDto(userDAO);

    }

    public UserInfoDTO updateUserInfo(int userId, UserRegistrationDTO body) throws Exception {

        /*Optional<UserDAO> userDAOOptional = userRepository.findById(userId);

        if(userDAOOptional == null || userDAOOptional.isEmpty()) {

            throw new Exception("No user found with ID: " + userId);

        }*/

        // DTO -> DAO
        UserDTOMapper userDTOMapper = applicationContext.getBean(UserDTOMapper.class);
        UserDAO userDAO = userDTOMapper.registrationDtoToDao(body);
        userDAO.setUserId(userId);

        UserDAO savedUserDAO = userRepository.save(userDAO);

        return userDTOMapper.userInfoDaoToDto(savedUserDAO);

    }
}
