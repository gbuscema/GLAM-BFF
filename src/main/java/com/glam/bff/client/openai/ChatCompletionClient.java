package com.glam.bff.client.openai;

import java.util.List;
import java.util.Map;

import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glam.bff.dao.GarmentDAO;
import com.glam.bff.dto.garment.enums.LayoutEnum;
import com.glam.bff.mapper.wardrobe.GarmentDTOMapper;
import com.glam.bff.model.Match;
import com.glam.bff.model.Wardrobe;
import com.glam.bff.utils.DAORelevantDataUtils;
import com.glam.bff.utils.OpenAIUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatCompletionClient {

    @Autowired
    private AiClient openAiApi;

    @Autowired
    private DAORelevantDataUtils daoRelevantDataUtils;

    @Autowired
    private GarmentDTOMapper garmentDTOMapper;

    public <T> AiResponse createChatCompletion(String content, Wardrobe wardrobe, Class<T> outputClass) {

        // columns constraint sentence
        String columnSentence = OpenAIUtils.getColumnConstraintWardrobeSentence(wardrobe);

        BeanOutputParser<T> outputParser = new BeanOutputParser<>(outputClass);
        // generate prompt for user message
        PromptTemplate promptTemplate = new PromptTemplate(content + columnSentence);
        promptTemplate.add("responseFormat", outputParser.getFormat());
        Prompt prompt = promptTemplate.create();
        log.debug("prompt: {}", prompt);
        return openAiApi.generate(prompt);
    }

    public <T> AiResponse createTodayOutfit(Map<String, Object> parameters, List<GarmentDAO> garments, Class<T> outputClass) {

        // columns constraint sentence
        List<Match> garmentsMatches = garmentDTOMapper.garmentsDaoToMatch(garments);
        String rawPrompt = OpenAIUtils.getTodayOutfitPrompt(garmentsMatches, daoRelevantDataUtils.getRelevantData(GarmentDAO.class), LayoutEnum.valueOf(parameters.get("layout").toString()));

        BeanOutputParser<T> outputParser = new BeanOutputParser<>(outputClass);
        // generate prompt for user message
        PromptTemplate promptTemplate = new PromptTemplate(rawPrompt);
        promptTemplate.add("locationInfo", OpenAIUtils.getLocationInfo(parameters.get("location").toString(), parameters.get("date").toString()));
        promptTemplate.add("responseFormat", outputParser.getFormat());
        Prompt prompt = promptTemplate.create();
        log.debug("prompt: {}", prompt);
        return openAiApi.generate(prompt);
    }
}
