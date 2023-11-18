package com.glam.bff.repository;

import com.glam.bff.dao.SubcategoryDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubcategoryRepository extends MongoRepository<SubcategoryDAO, String> {
}
