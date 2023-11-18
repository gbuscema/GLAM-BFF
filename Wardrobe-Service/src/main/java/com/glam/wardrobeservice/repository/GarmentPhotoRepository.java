package com.glam.wardrobeservice.repository;

import com.glam.wardrobeservice.dao.GarmentPhotoDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarmentPhotoRepository extends MongoRepository<GarmentPhotoDAO, String> {

}
