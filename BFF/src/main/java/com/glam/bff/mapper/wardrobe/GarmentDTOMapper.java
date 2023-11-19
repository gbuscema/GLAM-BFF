package com.glam.bff.mapper.wardrobe;

import java.util.Collections;
import java.util.List;

import com.glam.bff.dto.garment.BasicGarmentDTO;
import com.glam.bff.dto.garment.GarmentDTO;

import com.glam.bff.dto.garment.enums.*;
import com.glam.bff.openapi.wardrobe.model.GarmentDAO;
import com.google.cloud.vision.v1.AnnotateImageResponse;

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

    default BasicGarmentDTO visionApiToBasicDto(AnnotateImageResponse source) {
        var garment = new BasicGarmentDTO();
        var category = source.getLocalizedObjectAnnotations(0).getName();
        var mappedCategory = CategoryEnum.searchMatch(category);
        garment.setCategory(mappedCategory);

        var colors = source.getImagePropertiesAnnotation().getDominantColors().getColorsList();
        if (colors.size() > 0) {
            var firstColor = colors.get(0).getColor();
            garment.setMainColor(
                    ColorEnum.searchMatch(
                            firstColor.getRed(), firstColor.getGreen(), firstColor.getBlue()));
            if (colors.size() > 1) {
                var secondColor = colors.get(1).getColor();
                garment.setSecondColor(
                        ColorEnum.searchMatch(secondColor.getRed(), secondColor.getGreen(), secondColor.getBlue()));
            }
        }

        var annotations = source.getLabelAnnotationsList();
        annotations.forEach((annotation) -> {
            var valueToMatch = annotation.getDescription();
            var subcategory = SubCategoryEnum.searchMatch(valueToMatch);
            if (garment.getSubCategory() == null && subcategory != null) {
                garment.setSubCategory(subcategory);
                garment.setCategory(subcategory.getCategory());
                return;
            }

            var pattern = PatternEnum.searchMatch(valueToMatch);
            if (pattern != null) {
                garment.setPattern(pattern);
                return;
            }

            var sleeveLength = SleeveLengthEnum.searchMatch(valueToMatch);
            if (sleeveLength != null) {
                garment.setSleeveLength(sleeveLength);
                return;
            }

            var length = LengthEnum.searchMatch(valueToMatch);
            if (length != null) {
                garment.setLength(length);
                return;
            }

            var patternDetail = PatternDetailEnum.searchMatch(valueToMatch);
            if (patternDetail != null) {
                garment.setPatternDetail(patternDetail);
                return;
            }

            var fabric = FabricEnum.searchMatch(valueToMatch);
            if (fabric != null) {
                garment.setFabric(fabric);
                return;
            }

            SeasonEnum season = SeasonEnum.searchMatch(valueToMatch);
            if (season != null) {
                garment.setSeason(Collections.singletonList(season));
                return;
            }

            var size = SizeEnum.searchMatch(valueToMatch);
            if (size != null) {
                garment.setSizeEnum(size);
                return;
            }
        });

        var textDetected = source.getTextAnnotationsList();
        textDetected.forEach((text) -> {
            var valueToMatch = text.getDescription();

            var fabric = FabricEnum.searchMatch(valueToMatch);
            if (fabric != null) {
                garment.setFabric(fabric);
                return;
            }
            SeasonEnum season = SeasonEnum.searchMatch(valueToMatch);
            if (season != null) {
                garment.setSeason(Collections.singletonList(season));
                return;
            }

            var size = SizeEnum.searchMatch(valueToMatch);
            if (size != null) {
                garment.setSizeEnum(size);
                return;
            }
        });

        return garment;
    }
}