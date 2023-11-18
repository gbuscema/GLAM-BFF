package com.glam.bff.configuration;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glam.bff.repository.WardrobeVectorStore;
import com.glam.bff.utils.DAORelevantDataUtils;
import com.glam.bff.utils.WardrobeVectorStoreBuilder;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;

@Configuration
public class VectorStoreConfiguration {

    @Bean
    ConnectParam milvusConnectParam(){
        return ConnectParam.newBuilder()
//                .withDatabaseName("Glam")
                .build();
    }

    @Bean
    MilvusServiceClient milvusServiceClient(ConnectParam connectParam){
       return new MilvusServiceClient(connectParam);
    }

    @Bean
    WardrobeVectorStoreBuilder wardrobeRepository(MilvusServiceClient milvusServiceClient, EmbeddingClient embeddingClient, ObjectMapper objectMapper, DAORelevantDataUtils daoRelevantDataUtils) {

        WardrobeVectorStoreBuilder builder = new WardrobeVectorStoreBuilder();
        builder.setMilvusServiceClient(milvusServiceClient);
        builder.setEmbeddingClient(embeddingClient);
        builder.setObjectMapper(objectMapper);
        builder.setDaoRelevantDataUtils(daoRelevantDataUtils);

//        MilvusVectorStore.MilvusVectorStoreConfig config = MilvusVectorStore.MilvusVectorStoreConfig.builder()
////                .withDatabaseName("Glam")
//                .withCollectionName(WardrobeVectoreStore.COLLECTION_NAME)
//                .build();
        return builder;
    }

    @Bean
    WardrobeVectorStore wardrobeRepositoryBuilder(WardrobeVectorStoreBuilder builder) throws Exception {

//        WardrobeVectorStoreBuilder builder = new WardrobeVectorStoreBuilder();
//        builder.setMilvusServiceClient(milvusServiceClient);
//        builder.setEmbeddingClient(embeddingClient);
//        builder.setObjectMapper(objectMapper);
//        builder.setDaoRelevantDataUtils(daoRelevantDataUtils);
        builder.setCollectionName("1");
//        MilvusVectorStore.MilvusVectorStoreConfig config = MilvusVectorStore.MilvusVectorStoreConfig.builder()
////                .withDatabaseName("Glam")
//                .withCollectionName(WardrobeVectoreStore.COLLECTION_NAME)
//                .build();
        return builder.build();
    }

//    @Bean
//    ApplicationRunner saveWardrobe(UserWardrobeRepository repository, WardrobeVectoreStore vectoreStore) {
//        return args -> {
//            List<GarmentDAO> all = repository.findAll();
//            for (GarmentDAO garment : all) {
//                vectoreStore.save(garment);
//            }
//        };
//    }

//    @Bean
//    ApplicationRunner saveWardrobe(ResourceLoader resourceLoader, WardrobeRepository wardrobeRepository, ObjectMapper objectMapper) throws IOException {
//        Resource fileResource = resourceLoader.getResource("classpath:mockup/mockWardrobe.json");
//        List<Garment> garmentList = Arrays.asList((Garment[]) convertJsonFileIntoObject(fileResource.getInputStream(), Garment[].class));
//
//        List<Document> documents = garmentList.stream().map(g -> {
//            try {
//                return new Document(objectMapper.writeValueAsString(g));
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        }).toList();
//
//        return args -> {
//            wardrobeRepository.add(documents);
//        };
//    }

}
