package com.glam.bff.controller;

import com.glam.bff.dto.recommendation.RecommendationChatRequestDTO;
import com.glam.bff.dto.recommendation.RecommendationContextRequestDTO;
import com.glam.bff.dto.recommendation.UserRecommendationDTO;
import com.glam.bff.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.glam.bff.utils.Constants.USER_ID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    @Autowired
    private SuggestionService suggestionService;

    @PostMapping("/users/{userId}/chat")
    public UserRecommendationDTO getUserOutfitRecommendationChat(
            @PathVariable(USER_ID)
            final String userId,
            @RequestBody
            final RecommendationChatRequestDTO body) throws Exception {

        return suggestionService.getUserOutfitChatSuggestions(body);

    }

    @PostMapping("/users/{userId}/context")
    public UserRecommendationDTO getUserOutfitRecommendationContext(
            @PathVariable(USER_ID)
            final String userId,
            @RequestBody
            final RecommendationContextRequestDTO body
    ){

        return new UserRecommendationDTO();

    }

    @PostMapping(path = "/users/{userId}/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserRecommendationDTO getUserOutfitRecommendationAudio(
            @PathVariable(USER_ID)
            final String userId,
            @RequestPart("audio") MultipartFile part){

        return new UserRecommendationDTO();

    }

}
