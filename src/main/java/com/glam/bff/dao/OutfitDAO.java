package com.glam.bff.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutfitDAO {

    @Id
    private String outfitId;

    private String userId;

    private String name;

    private String layout;

    private Boolean isFavorite;

    @DocumentReference
    private List<GarmentDAO> garmentList;

}
