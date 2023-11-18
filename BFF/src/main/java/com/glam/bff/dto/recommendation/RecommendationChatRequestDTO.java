package com.glam.bff.dto.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationChatRequestDTO {

    @JsonProperty("chatPrompt")
    private String chatPrompt;

}
