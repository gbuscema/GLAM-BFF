package com.glam.wardrobeservice.repository;

import com.glam.wardrobeservice.dao.OutfitDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOutfitRepository extends MongoRepository<OutfitDAO, String> {

    @Query("{ 'userId' : ?0 }")
    List<OutfitDAO> findOutfitsByUserId(String userId);

}
