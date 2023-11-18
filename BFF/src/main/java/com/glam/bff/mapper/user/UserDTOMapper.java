package com.glam.bff.mapper.user;

import com.glam.bff.dao.UserDAO;
import com.glam.bff.dto.authentication.UserInfoDTO;
import com.glam.bff.dto.authentication.UserLoginDTO;
import com.glam.bff.dto.authentication.UserLoginResponseDTO;
import com.glam.bff.dto.authentication.UserRegistrationDTO;
import com.glam.bff.dto.recommendation.UserMatchDTO;
import com.glam.bff.model.Match;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {
    UserRegistrationDTO registrationDaoToDto (UserDAO source);
    UserDAO registrationDtoToDao (UserRegistrationDTO source);

    UserLoginResponseDTO loginDaoToDto (UserDAO source);
    UserDAO loginDtoToDao (UserLoginDTO source);

    UserInfoDTO userInfoDaoToDto (UserDAO source);
    UserDAO userInfoDtoToDao (UserInfoDTO source);

}
