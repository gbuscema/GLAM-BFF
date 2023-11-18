package com.glam.bff.repository;

import com.glam.bff.dao.GarmentDAO;
import com.glam.bff.dao.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<UserDAO, String> {
    List<UserDAO> findUsersByEmailAndPassword(String email, String password);

}
