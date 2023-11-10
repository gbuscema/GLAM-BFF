package com.glam.bff.dto.garment;

import com.glam.bff.dto.garment.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicGarmentDTO {

    private String title;

    private String description;

    private CategoryEnum category;

    private SubCategoryEnum subCategory;

    private ColorEnum mainColor;

    private ColorEnum secondColor;

    private PatternEnum pattern;

    private String brand;

    private Boolean isFavorite;

    private FabricEnum fabric;

    private ConditionEnum condition;

    private SeasonEnum season;

    private SleeveLengthEnum sleeveLength;

    private LengthEnum length;

    private SizeEnum sizeEnum;

    private PatternDetailEnum patternDetail;

    private String photoUri;

    private String textureUri;

}
