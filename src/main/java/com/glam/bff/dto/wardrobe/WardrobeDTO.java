package com.glam.bff.dto.wardrobe;

import com.glam.bff.dto.garment.GarmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardrobeDTO {

    private String userId;

    private List<GarmentDTO> garmentList;

}
