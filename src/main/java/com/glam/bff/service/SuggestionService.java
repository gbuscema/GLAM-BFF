package com.glam.bff.service;

import static com.glam.bff.utils.JsonUtils.convertJsonFileIntoObject;
import static com.glam.bff.utils.JsonUtils.convertJsonIntoOutfit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import com.glam.bff.dto.outfit.TodayOutfitDTO;
import com.glam.bff.dto.recommendation.RecommendationChatRequestDTO;
import com.glam.bff.dto.recommendation.UserMatchDTO;
import com.glam.bff.dto.recommendation.UserRecommendationDTO;
import com.glam.bff.mapper.recommendation.UserMatchDTOMapper;
import com.glam.bff.mapper.wardrobe.GarmentDTOMapper;
import com.glam.bff.model.Garment;
import com.glam.bff.model.Match;
import com.glam.bff.model.Outfit;
import com.glam.bff.model.Wardrobe;
import com.glam.bff.repository.UserWardrobeRepository;
import com.glam.bff.repository.WardrobeVectoreStore;

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
    private WardrobeVectoreStore wardrobeVectoreStore;

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
        List<Garment> garmentList = Arrays.asList((Garment[]) convertJsonFileIntoObject(fileResource.getInputStream(), Garment[].class));
        wardrobe.setGarments(garmentList);

        // 1. Get suggestions from OpenAI
        AiResponse createChatCompletionResponse = chatCompletionClient
                 .createChatCompletion(body.getChatPrompt(), wardrobe, GarmentDTO.class);
        String jsonStringResponse = createChatCompletionResponse.getGeneration().getText();
        log.debug("jsonStringResponse {}", jsonStringResponse);
        Outfit outfit = convertJsonIntoOutfit(jsonStringResponse);

//        UserMatchDTOMapper userMatchDTOMapper =
//                applicationContext.getBean(UserMatchDTOMapper.class);

        // 2. For each suggestion -> find nearest with wardrobe
        for(Garment garment : outfit.getMatches()){

            List<Document> retrievedDocuments = new VectorStoreRetriever(wardrobeVectoreStore, 10).retrieve(garment.toString());

            for(Document document : retrievedDocuments){
                Match match = objectMapper.readValue(document.getContent(), Match.class);
                if(match.getCategory().equals(garment.getCategory())){
                    userWardrobeRepository.findByGarmentId(document.getMetadata().get("garmentId").toString());
                }
            }


////            Match match = knnClient.getNearestOutfit(garment, garmentWeight, wardrobeTempFile);
//            UserMatchDTO userMatchDTO = userMatchDTOMapper.modelToDTO(match);
//            matches.add(userMatchDTO);

        }

        // 3. Return outfit matches
        UserRecommendationDTO userRecommendationDTO = new UserRecommendationDTO();
        userRecommendationDTO.setMatches(matches);
        return userRecommendationDTO;

    }

    public TodayOutfitDTO todayOutfit(Map<String, Object> parameters) throws JsonProcessingException {

        TodayOutfitDTO result = new TodayOutfitDTO();
//        List<UserMatchDTO> matches = new ArrayList<>();

        // 0. Mock - get user wardrobe

        List<GarmentDAO> allGarmets = userWardrobeRepository.findAll();
//        Resource fileResource = resourceLoader.getResource("classpath:mockup/mockWardrobe.json");
//        Wardrobe wardrobe = wardrobeRepository.retrieveWardRobe();
//        Wardrobe  wardrobe = new Wardrobe();
//        List<Garment> garmentList = Arrays.asList((Garment[]) convertJsonFileIntoObject(fileResource.getInputStream(), Garment[].class));
//        wardrobe.setGarments(garmentList);

        // 1. Get suggestions from OpenAI
        AiResponse createChatCompletionResponse = chatCompletionClient
                .createTodayOutfit(parameters, allGarmets, Outfit.class);
        String jsonStringResponse = createChatCompletionResponse.getGeneration().getText();
        log.debug("jsonStringResponse {}", jsonStringResponse);
        Outfit outfit = convertJsonIntoOutfit(jsonStringResponse);

//        UserMatchDTOMapper userMatchDTOMapper =
//                applicationContext.getBean(UserMatchDTOMapper.class);

        List<GarmentDTO> garmentList = new ArrayList<>();

        // 2. For each suggestion -> find nearest with wardrobe
        for(Garment garment : outfit.getMatches()){

            List<Document> retrievedDocuments = new VectorStoreRetriever(wardrobeVectoreStore, 10).retrieve(garment.toString());

            for(Document document : retrievedDocuments){
                Match match = objectMapper.readValue(document.getContent(), Match.class);
                if(match.getCategory().equals(garment.getCategory())){
                    GarmentDAO garmentDao = userWardrobeRepository.findByGarmentId(document.getMetadata().get("garmentId").toString());
                    garmentList.add(garmentDTOMapper.daoToDto(garmentDao));
                }
            }


////            Match match = knnClient.getNearestOutfit(garment, garmentWeight, wardrobeTempFile);
//            UserMatchDTO userMatchDTO = userMatchDTOMapper.modelToDTO(match);
//            matches.add(userMatchDTO);

        }

        // 3. Return outfit matches
        result.setLayout(LayoutEnum.valueOf(parameters.get("layout").toString()));
        result.setGarmentList(garmentList);
        return result;


    }
}
