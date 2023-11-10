package com.glam.bff.mapper.wardrobe;

import com.glam.bff.dao.GarmentPhotoDAO;
import com.glam.bff.dto.garment.GarmentPhotoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GarmentPhotoDTOMapper {

    GarmentPhotoDTO daoToDto(GarmentPhotoDAO source);

    GarmentPhotoDAO dtoToDao(GarmentPhotoDTO source);
}
