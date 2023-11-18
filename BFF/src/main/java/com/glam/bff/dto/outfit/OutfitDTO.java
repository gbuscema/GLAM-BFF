package com.glam.bff.dto.outfit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutfitDTO extends BasicOutfitDTO {

    private String outfitId;

    private String userId;

}
