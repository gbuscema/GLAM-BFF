package com.glam.bff.mapper.outfit;

import com.glam.bff.dao.OutfitDAO;
import com.glam.bff.dto.outfit.BasicOutfitDTO;
import com.glam.bff.dto.outfit.OutfitDTO;
import com.glam.bff.mapper.wardrobe.GarmentDTOMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {GarmentDTOMapper.class})
public interface OutfitDTOMapper {

    OutfitDTO daoToDto(OutfitDAO source);

    OutfitDAO dtoToDao(OutfitDTO source);

    BasicOutfitDTO basicDaoToDto(OutfitDAO source);

    OutfitDAO basicDtoToDao(BasicOutfitDTO source);


}
