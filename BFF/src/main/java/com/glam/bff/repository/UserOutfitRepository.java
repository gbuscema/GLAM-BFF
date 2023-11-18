package com.glam.bff.repository;

import com.glam.bff.dao.GarmentDAO;
import com.glam.bff.dao.OutfitDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOutfitRepository extends MongoRepository<OutfitDAO, String> {

    @Query("{ 'userId' : ?0 }")
    List<OutfitDAO> findOutfitsByUserId(String userId);

}
