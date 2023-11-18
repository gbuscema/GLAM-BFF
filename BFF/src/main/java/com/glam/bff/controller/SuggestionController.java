package com.glam.bff.controller;

import static com.glam.bff.utils.Constants.LAYOUT;
import static com.glam.bff.utils.Constants.LOCATION;
import static com.glam.bff.utils.Constants.USER_ID;
import static com.glam.bff.utils.Constants.USER_PROMPT;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.glam.bff.dto.garment.enums.LayoutEnum;
import com.glam.bff.dto.outfit.TodayOutfitDTO;
import com.glam.bff.dto.recommendation.RecommendationChatRequestDTO;
import com.glam.bff.dto.recommendation.UserRecommendationDTO;
import com.glam.bff.service.SuggestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/suggestion")
public class SuggestionController {


    @Autowired
    private SuggestionService suggestionService;

    @GetMapping("/outfit/today")
    @Tag(name = "Suggestion")
    @Operation(
            summary = "Retrieve the today outfit",
            description ="Retrieve the today outfit based on user preferences and location")
    @Parameters({
            @Parameter(name = USER_ID, required = true, example = "1", schema = @Schema(implementation = String.class)),
            @Parameter(name = LAYOUT, example = "GENERIC", schema = @Schema(implementation = LayoutEnum.class)),
            @Parameter(name = LOCATION, example = "Ibiza", schema = @Schema(implementation = String.class))
    })
    public TodayOutfitDTO todayOutfit(@RequestParam Map<String, Object> parameters) throws JsonProcessingException {
        parameters.putIfAbsent(LAYOUT, LayoutEnum.GENERIC.name());
        return suggestionService.todayOutfit(parameters);
    }

    @GetMapping("/outfit/suggested")
    @Tag(name = "Suggestion")
    @Operation(
            summary = "Retrieve an outfit given a prompt",
            description ="Retrieve an outfit given a textual prompt by user")
    @Parameters({
            @Parameter(name = USER_ID, required = true, example = "1", schema = @Schema(implementation = String.class)),
            @Parameter(name = USER_PROMPT, example = "I want a total black outfit", required = true, schema = @Schema(implementation = String.class))
    })
    public TodayOutfitDTO suggestedOutfit(@RequestParam(USER_ID) String userId, @RequestParam(USER_PROMPT) String prompt) throws JsonProcessingException {

        Map<String, Object> map = new HashMap<>();
        map.put(USER_ID, userId);
        map.put(USER_PROMPT, prompt);
        map.putIfAbsent(LAYOUT, LayoutEnum.GENERIC.name());
        return suggestionService.todayOutfit(map);
    }
}
