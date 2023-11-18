package com.glam.bff.utils;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.MilvusVectorStore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glam.bff.repository.WardrobeVectorStore;

import io.milvus.client.MilvusServiceClient;
import lombok.Data;

@Data
public class WardrobeVectorStoreBuilder {

    private MilvusServiceClient milvusServiceClient;
    private EmbeddingClient embeddingClient;
    private MilvusVectorStore.MilvusVectorStoreConfig config;
    private ObjectMapper objectMapper;
    private DAORelevantDataUtils daoRelevantDataUtils;

    private WardrobeVectorStore wardrobeVectorStore;
    private String collectionName;

    public void setCollectionName(String userId){
        this.collectionName = WardrobeVectorStore.COLLECTION_NAME_PREFIX + userId;
    }

    public WardrobeVectorStore build() throws Exception {
        MilvusVectorStore.MilvusVectorStoreConfig config = MilvusVectorStore.MilvusVectorStoreConfig.builder()
//                .withDatabaseName("Glam")
                .withCollectionName(collectionName)
                .build();
        wardrobeVectorStore = new WardrobeVectorStore(milvusServiceClient, embeddingClient, config, objectMapper, daoRelevantDataUtils);
        wardrobeVectorStore.afterPropertiesSet();
        return wardrobeVectorStore;
    }


}
