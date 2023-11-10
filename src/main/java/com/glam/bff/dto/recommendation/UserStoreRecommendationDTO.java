package com.glam.bff.dto.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserStoreRecommendationDTO extends AbstractRecommendationDTO {

    @JsonProperty("storeId")
    private String storeId;

    @JsonProperty("matches")
    List<UserStoreMatchDTO> matches;

}
