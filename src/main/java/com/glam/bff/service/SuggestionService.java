package com.glam.bff.service;

import static com.glam.bff.dto.garment.enums.CategoryEnum.externalStoreMap;
import static com.glam.bff.utils.Constants.LAYOUT;
import static com.glam.bff.utils.Constants.USER_ID;
import static com.glam.bff.utils.JsonUtils.convertJsonIntoOutfit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.glam.bff.dto.garment.enums.CategoryEnum;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.retriever.VectorStoreRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glam.bff.client.machinelearning.KNNClient;
import com.glam.bff.client.openai.ChatCompletionClient;
import com.glam.bff.dao.GarmentDAO;
import com.glam.bff.dto.garment.GarmentDTO;
import com.glam.bff.dto.garment.enums.LayoutEnum;
import com.glam.bff.dto.outfit.BasicOutfitDTO;
import com.glam.bff.dto.outfit.TodayOutfitDTO;
import com.glam.bff.dto.recommendation.RecommendationChatRequestDTO;
import com.glam.bff.dto.recommendation.UserMatchDTO;
import com.glam.bff.dto.recommendation.UserRecommendationDTO;
import com.glam.bff.mapper.wardrobe.GarmentDTOMapper;
import com.glam.bff.model.Garment;
import com.glam.bff.model.Match;
import com.glam.bff.model.Outfit;
import com.glam.bff.model.Wardrobe;
import com.glam.bff.repository.UserWardrobeRepository;
import com.glam.bff.utils.WardrobeVectorStoreBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SuggestionService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ChatCompletionClient chatCompletionClient;

    @Autowired
    private KNNClient knnClient;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private WardrobeVectorStoreBuilder wardrobeVectorStoreBuilder;

    @Autowired
    private UserWardrobeRepository userWardrobeRepository;

    @Autowired
    private GarmentDTOMapper garmentDTOMapper;

    public UserRecommendationDTO getUserOutfitChatSuggestions(RecommendationChatRequestDTO body) throws IOException, IllegalAccessException {

        log.debug("RecommendationChatRequestDTO {}", body);
        List<UserMatchDTO> matches = new ArrayList<>();

        // 0. Mock - get user wardrobe

        Resource fileResource = resourceLoader.getResource("classpath:mockup/mockWardrobe.json");
//        Wardrobe wardrobe = wardrobeRepository.retrieveWardRobe();
        Wardrobe  wardrobe = new Wardrobe();
//        List<Garment> garmentList = Arrays.asList((Garment[]) convertJsonFileIntoObject(fileResource.getInputStream(), Garment[].class));
        wardrobe.setGarments(new ArrayList<>());

        // 1. Get suggestions from OpenAI
        AiResponse createChatCompletionResponse = chatCompletionClient
                 .createChatCompletion(body.getChatPrompt(), wardrobe, GarmentDTO.class);
        String jsonStringResponse = createChatCompletionResponse.getGeneration().getText();
        log.debug("jsonStringResponse {}", jsonStringResponse);
        Outfit outfit = convertJsonIntoOutfit(jsonStringResponse);


        // 2. For each suggestion -> find nearest with wardrobe
        for(Garment garment : outfit.getMatches()){

            List<Document> retrievedDocuments = new VectorStoreRetriever(wardrobeVectorStoreBuilder.getWardrobeVectorStore(), 10).retrieve(garment.toString());

            for(Document document : retrievedDocuments){
                Match match = objectMapper.readValue(document.getContent(), Match.class);
                if(match.getCategory().equals(garment.getCategory())){
                    userWardrobeRepository.findByGarmentId(document.getMetadata().get("garmentId").toString());
                }
            }

        }

        // 3. Return outfit matches
        UserRecommendationDTO userRecommendationDTO = new UserRecommendationDTO();
        userRecommendationDTO.setMatches(matches);
        return userRecommendationDTO;

    }

    public TodayOutfitDTO todayOutfit(Map<String, Object> parameters) throws JsonProcessingException {
        TodayOutfitDTO result = new TodayOutfitDTO();

        // 0. Mock - get user wardrobe
        log.debug("parameters {}", parameters);
        List<GarmentDAO> allGarments = userWardrobeRepository.findGarmentsByUserId(parameters.get(USER_ID).toString());
        log.debug("allGarments: {}", allGarments);

        // 1. Get suggestions from OpenAI
        AiResponse createChatCompletionResponse = chatCompletionClient
                .suggestOutfit(parameters, allGarments, Outfit.class);
        String jsonStringResponse = createChatCompletionResponse.getGeneration().getText();
        log.debug("jsonStringResponse {}", jsonStringResponse);
        Outfit outfit = convertJsonIntoOutfit(jsonStringResponse);

        List<GarmentDTO> garmentList = new ArrayList<>();

        // 2. For each suggestion -> find nearest with wardrobe
        for(Garment garment : outfit.getMatches()){

            List<Document> retrievedDocuments = new VectorStoreRetriever(wardrobeVectorStoreBuilder.getWardrobeVectorStore(), 5).retrieve(garment.toString());

            for(Document document : retrievedDocuments){
                Match match = objectMapper.readValue(document.getContent(), Match.class);
                if(match != null && match.getCategory() != null && match.getCategory().equalsIgnoreCase(garment.getCategory())){
                    GarmentDAO garmentDao = userWardrobeRepository.findByGarmentId(document.getId());
                    garmentList.add(garmentDTOMapper.daoToDto(garmentDao));
                    break;
                }
            }

        }

        // 3. Return outfit matches
        result.setLayout(LayoutEnum.valueOf(parameters.get(LAYOUT).toString()));
        List<GarmentDTO> filteredGarmentList = garmentList
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));

        // 4. Understand which garment weren't found from wardrobe
        outfit.getMatches().forEach(match -> {

            try {

                String categoryMatch = match.getCategory().toUpperCase();
                CategoryEnum categoryEnum = CategoryEnum.valueOf(categoryMatch);
                if(categoryMatch != null) {

                    List<GarmentDTO> garmentDTOList = filteredGarmentList
                            .stream()
                            .filter(garmentDTO -> garmentDTO.getCategory().getValue().equalsIgnoreCase(categoryMatch))
                            .toList();

                    if(garmentDTOList.isEmpty()){
                        GarmentDTO emptyGarmentPlaceholder = new GarmentDTO();
                        emptyGarmentPlaceholder.setCategory(categoryEnum);
                        if(categoryEnum != null && externalStoreMap.containsKey(categoryEnum)){

                            String externalUrl = externalStoreMap.get(categoryEnum);
                            emptyGarmentPlaceholder.setExternalStoreUrl(externalUrl);

                        }
                        filteredGarmentList.add(emptyGarmentPlaceholder);
                    }

                }

            } catch (Exception e) {

                log.error(e.getMessage(), e);

            }

        });

        result.setGarmentList(filteredGarmentList);
        return result;
    }

    public BasicOutfitDTO promptedOutfit(Map<String, Object> parameters){
        BasicOutfitDTO result = new BasicOutfitDTO();
//        List<UserMatchDTO> matches = new ArrayList<>();

        // 0. Mock - get user wardrobe

        List<GarmentDAO> allGarments = userWardrobeRepository.findGarmentsByUserId(parameters.get("userId").toString());
        log.debug("allGarments: {}", allGarments);

        AiResponse createChatCompletionResponse = chatCompletionClient
                .suggestOutfit(parameters, allGarments, Outfit.class);
        return null;
    }
}
