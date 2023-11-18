package com.glam.bff.repository;

import com.glam.bff.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, String> {
    List<UserDAO> findUsersByEmailAndPassword(String email, String password);

    UserDAO save(UserDAO entity);

    Optional<UserDAO> findById(String userId);

}
