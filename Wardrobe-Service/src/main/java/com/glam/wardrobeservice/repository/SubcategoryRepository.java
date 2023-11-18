package com.glam.wardrobeservice.repository;

import com.glam.wardrobeservice.dao.SubcategoryDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubcategoryRepository extends MongoRepository<SubcategoryDAO, String> {
}
