package com.glam.bff.dto.outfit;

import com.glam.bff.dto.garment.GarmentDTO;
import com.glam.bff.dto.garment.enums.LayoutEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicOutfitDTO {

    private String name;

    private LayoutEnum layout;

    private Boolean isFavorite;

    private List<GarmentDTO> garmentList;

}
