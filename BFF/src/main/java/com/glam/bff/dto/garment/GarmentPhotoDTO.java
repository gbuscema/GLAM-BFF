package com.glam.bff.dto.garment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarmentPhotoDTO {

    private String photoId;

    private String userId;

    private String fileName;

    private byte[] photo;
}
