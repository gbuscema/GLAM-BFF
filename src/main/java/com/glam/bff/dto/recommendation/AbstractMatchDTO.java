package com.glam.bff.dto.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractMatchDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("color")
    private String color;

    @JsonProperty("size")
    private String size;

    @JsonProperty("garmentType")
    private String garmentType;

    @JsonProperty("fabric")
    private String fabric;

}
