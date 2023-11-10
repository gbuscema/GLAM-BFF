package com.glam.bff.repository;

import java.util.Collections;
import java.util.HashMap;
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
public class WardrobeVectoreStore extends MilvusVectorStore {

    public static final String COLLECTION_NAME = "wardrobe";

    private final ObjectMapper objectMapper;

    private final DAORelevantDataUtils daoRelevantDataUtils;

    public WardrobeVectoreStore(MilvusServiceClient milvusServiceClient, EmbeddingClient embeddingClient,
            MilvusVectorStoreConfig config, ObjectMapper objectMapper, DAORelevantDataUtils daoRelevantDataUtils) {
        super(milvusServiceClient, embeddingClient, config);
        this.objectMapper = objectMapper;
        this.daoRelevantDataUtils = daoRelevantDataUtils;
    }

    public void save(GarmentDAO garment) {
        Map<String, Object> objectAsMap = objectMapper
                .convertValue(garment, new TypeReference<>() {});
        objectAsMap.keySet().removeIf(k -> !daoRelevantDataUtils.getRelevantData(GarmentDAO.class).contains(k));

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("garmentId", garment.getGarmentId());
        try{
            Document document = new Document(objectMapper.writeValueAsString(objectAsMap), metadata);
            super.add(Collections.singletonList(document));
        }catch (JsonProcessingException ex){
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

}
