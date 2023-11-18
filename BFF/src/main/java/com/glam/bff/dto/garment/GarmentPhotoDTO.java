package com.glam.bff.dto.garment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarmentPhotoDTO {

    private String photoId;

    private String userId;

    private String fileName;

    private byte[] photo;
}
