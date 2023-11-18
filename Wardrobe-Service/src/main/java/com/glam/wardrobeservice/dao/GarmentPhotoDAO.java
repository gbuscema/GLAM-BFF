package com.glam.wardrobeservice.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarmentPhotoDAO {

    @Id
    private String photoId;

    @DocumentReference
    private GarmentDAO garment;

    private String userId;

    private String fileName;

    private byte[] photo;

    private String textureUri;
}
