package com.glam.wardrobeservice.repository;

import com.glam.wardrobeservice.dao.GarmentDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWardrobeRepository extends MongoRepository<GarmentDAO, String> {

    @Query("{ 'userId' : ?0 }")
    List<GarmentDAO> findGarmentsByUserId(String userId);

    //@Query("{ 'userId': ?0, 'category': ?0}")
    List<GarmentDAO> findGarmentsByUserIdAndCategory(String userId, String category);

    GarmentDAO findByGarmentId(String garmentId);
}
