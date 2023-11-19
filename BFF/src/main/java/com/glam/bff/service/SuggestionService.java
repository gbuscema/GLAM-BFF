package com.glam.bff.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.glam.bff.dto.outfit.BasicOutfitDTO;
import com.glam.bff.dto.outfit.TodayOutfitDTO;
import com.glam.bff.dto.recommendation.RecommendationChatRequestDTO;
import com.glam.bff.dto.recommendation.UserRecommendationDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SuggestionService {

    public UserRecommendationDTO getUserOutfitChatSuggestions(RecommendationChatRequestDTO body) throws IOException, IllegalAccessException {

        UserRecommendationDTO userRecommendationDTO = new UserRecommendationDTO();

        return userRecommendationDTO;

    }

    public TodayOutfitDTO todayOutfit(Map<String, Object> parameters) throws JsonProcessingException {

        TodayOutfitDTO result = new TodayOutfitDTO();

        return result;

    }

    public BasicOutfitDTO promptedOutfit(Map<String, Object> parameters){

        BasicOutfitDTO result = new BasicOutfitDTO();

        return result;

    }
}
