package com.glam.bff.mapper.wardrobe;

import com.glam.bff.dto.garment.GarmentPhotoDTO;
import com.glam.bff.openapi.wardrobe.model.GarmentPhotoDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.FileNotFoundException;
import java.io.IOException;

@Mapper(componentModel = "spring")
public interface GarmentPhotoDTOMapper {

    GarmentPhotoDTO daoToDto(GarmentPhotoDAO source);

}
