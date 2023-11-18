package com.glam.bff.repository;

import java.util.Collections;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.MilvusVectorStore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glam.bff.dao.GarmentDAO;
import com.glam.bff.utils.DAORelevantDataUtils;

import io.milvus.client.MilvusServiceClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WardrobeVectorStore extends MilvusVectorStore {

    public static final String COLLECTION_NAME_PREFIX = "wardrobe_";

    private final ObjectMapper objectMapper;

    private final DAORelevantDataUtils daoRelevantDataUtils;

    public WardrobeVectorStore(MilvusServiceClient milvusServiceClient, EmbeddingClient embeddingClient,
            MilvusVectorStoreConfig config, ObjectMapper objectMapper, DAORelevantDataUtils daoRelevantDataUtils) {
        super(milvusServiceClient, embeddingClient, config);
        this.objectMapper = objectMapper;
        this.daoRelevantDataUtils = daoRelevantDataUtils;
    }

    public void save(GarmentDAO garment) {
        Map<String, Object> objectAsMap = objectMapper
                .convertValue(garment, new TypeReference<>() {
                });
        objectAsMap.keySet().removeIf(k -> !daoRelevantDataUtils.getRelevantData(GarmentDAO.class).contains(k));

        try {
            Document document = new Document(garment.getGarmentId(), objectMapper.writeValueAsString(objectAsMap), Collections.emptyMap());
            super.add(Collections.singletonList(document));
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    public void delete(String garmentId) {
        super.delete(Collections.singletonList(garmentId));
    }
    public void update(GarmentDAO garment) {
       this.delete(garment.getGarmentId());
       this.save(garment);
    }

}
