package com.glam.userservice.service;

import com.glam.userservice.dao.UserDAO;
import com.glam.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserRepository userRepository;

    public void registerUser(UserDAO userDAO) {

        userRepository.save(userDAO);

    }

    public UserDAO loginUser(UserDAO body) throws Exception {

        List<UserDAO> userDAOList = userRepository
                .findUsersByEmailAndPassword(body.getEmail(), body.getPassword());

        if(userDAOList == null || userDAOList.isEmpty()){
            throw new Exception("Wrong Email or Password!");
        }

        UserDAO userDAO = userDAOList.get(0);

        return userDAO;

    }

    public UserDAO getUserInfo(String userId) throws Exception {

        Optional<UserDAO> userDAOOptional = userRepository.findById(userId);
        if(userDAOOptional.isEmpty()){
            throw new Exception("No User found for ID: " + userId);
        }

        return userDAOOptional.get();

    }

    public UserDAO updateUserInfo(int userId, UserDAO body) throws Exception {

        body.setUserId(userId);

        return userRepository.save(body);

    }
}
