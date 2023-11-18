package com.glam.bff.dao;

import com.glam.bff.dto.wardrobe.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarmentDAO {

    @Id
    private String garmentId;

    @DocumentReference
    private GarmentPhotoDAO photo;

    @Indexed
    private String userId;

    private String title;

    private String description;

    private String category;

    private String subCategory;

    private String mainColor;

    private String secondColor;

    private String pattern;

    private String brand;

    private Boolean isFavorite;

    private String fabric;

    private String condition;

    private String sleeveLength;

    private String length;

    private String sizeEnum;

    private String patternDetail;

    private List<String> season;

    private List<String> styles;

}
