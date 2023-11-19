package com.glam.wardrobeservice.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarmentPhotoDAO {

    @Id
    private String photoId;

    private String garmentId;

    private String userId;

    private String fileName;

    @Schema(name = "photo", type = "string", format = "byte")
    private byte[] photo;

    private String textureUri;
}
