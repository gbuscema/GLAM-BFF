package com.glam.bff.mapper.recommendation;

import com.glam.bff.dto.recommendation.UserMatchDTO;
import com.glam.bff.model.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMatchDTOMapper {

//    @Mapping(target = "color", source = "source.color")
//    @Mapping(target = "size", source = "source.size")
//    @Mapping(target = "garmentType", source = "source.garmentType")
//    @Mapping(target = "fabric", source = "source.fabric")
    UserMatchDTO modelToDTO(Match source);
}
