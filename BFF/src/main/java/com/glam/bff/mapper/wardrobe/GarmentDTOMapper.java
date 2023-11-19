package com.glam.bff.mapper.wardrobe;

import java.util.Collections;
import java.util.List;

import com.glam.bff.dto.garment.BasicGarmentDTO;
import com.glam.bff.dto.garment.GarmentDTO;

import com.glam.bff.dto.garment.enums.*;
import com.glam.bff.openapi.wardrobe.model.GarmentDAO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GarmentDTOMapper {

    @Mapping(target = "photoUri", expression = "java( source.getPhoto() != null ? \"/wardrobes/users/\" + source.getUserId() + \"/garments/photo/\" + source.getPhoto().getPhotoId() : null)")
    @Mapping(target = "textureUri", source = "source.photo.textureUri")
    GarmentDTO daoToDto(GarmentDAO source);

    GarmentDAO dtoToDao(GarmentDTO source);

    @Mapping(target = "photoUri", expression = "java( source.getPhoto() != null ? \"/wardrobes/users/\" + source.getUserId() + \"/garments/photo/\" + source.getPhoto().getPhotoId() : null)")
    @Mapping(target = "textureUri", source = "source.photo.textureUri")
    BasicGarmentDTO basicDaoToDto(GarmentDAO source);

    GarmentDAO basicDtoToDao(BasicGarmentDTO source);

    GarmentDTO basicDTOtoDTO(BasicGarmentDTO source);

}