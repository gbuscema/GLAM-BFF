package com.glam.bff.service;

import com.glam.bff.client.wardrobe.OutfitClient;
import com.glam.bff.client.wardrobe.WardrobeClient;
import com.glam.bff.dto.outfit.BasicOutfitDTO;
import com.glam.bff.dto.outfit.OutfitDTO;
import com.glam.bff.mapper.outfit.OutfitDTOMapper;
import com.glam.bff.openapi.wardrobe.model.OutfitDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OutfitService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WardrobeClient wardrobeClient;
    @Autowired
    private OutfitClient outfitClient;

    public List<OutfitDTO> getUserOutfits(String userId) {

        // get outfits from DB per userId
        List<OutfitDAO> outfitDAOList = outfitClient.getUserOutfits(userId);

        // DAO -> DTO
        OutfitDTOMapper outfitDTOMapper = applicationContext.getBean(OutfitDTOMapper.class);
        return outfitDAOList
                .stream()
                .map(outfitDTOMapper::daoToDto)
                .toList();

    }

    public OutfitDTO saveUserOutfit(String userId, BasicOutfitDTO userOutfit) {

        // DTO -> DAO
        OutfitDTOMapper outfitDTOMapper = applicationContext.getBean(OutfitDTOMapper.class);
        OutfitDAO outfitDAO = outfitDTOMapper.basicDtoToDao(userOutfit);
        outfitDAO.setUserId(userId);

        // save user outfit inside DB
        OutfitDAO savedOutfitDAO = outfitClient.saveUserOutfit(userId, outfitDAO);

        return outfitDTOMapper.daoToDto(savedOutfitDAO);

    }

    public void deleteUserOutfit(String outfitId, String userId) throws Exception {

        outfitClient.deleteUserOutfit(outfitId, userId);

    }

    public OutfitDTO updateUserOutfit(String outfitId, String userId, BasicOutfitDTO outfitDTO) throws Exception {

        // DTO -> DAO
        OutfitDTOMapper outfitDTOMapper = applicationContext.getBean(OutfitDTOMapper.class);
        OutfitDAO newOutfitDAO = outfitDTOMapper.basicDtoToDao(outfitDTO);

        OutfitDAO updatedOutfitDAO = outfitClient.updateUserOutfit(outfitId, userId, newOutfitDAO);

        // DAO -> DTO
        return outfitDTOMapper.daoToDto(updatedOutfitDAO);

    }

    public OutfitDTO getUserOutfit(String outfitId, String userId) throws Exception {

        OutfitDAO outfitDAO = outfitClient.getUserOutfit(outfitId, userId);

        // DTO -> DAO
        OutfitDTOMapper outfitDTOMapper = applicationContext.getBean(OutfitDTOMapper.class);
        return outfitDTOMapper.daoToDto(outfitDAO);

    }

    public OutfitDTO updateUserOutfitFavorite(String outfitId, String userId, Boolean isFavorite) throws Exception {

        OutfitDAO outfitDAO = outfitClient.updateUserOutfitFavorite(outfitId, userId, isFavorite);

        // DAO -> DTO
        OutfitDTOMapper outfitDTOMapper = applicationContext.getBean(OutfitDTOMapper.class);
        return outfitDTOMapper.daoToDto(outfitDAO);

    }
}
