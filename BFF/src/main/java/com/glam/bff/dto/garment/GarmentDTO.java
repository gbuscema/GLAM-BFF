package com.glam.bff.dto.garment;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.IOException;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GarmentDTO extends BasicGarmentDTO {

    private String garmentId;

    private String externalStoreUrl;

    public static GarmentDTO getJson(String user) throws IOException {

        GarmentDTO garmentJson = new GarmentDTO();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            garmentJson = objectMapper.readValue(user, GarmentDTO.class);
        } catch (IOException err) {
            System.out.printf("Error", err.toString());
        }

        return garmentJson;

    }

}
