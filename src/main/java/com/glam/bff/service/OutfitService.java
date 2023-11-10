package com.glam.bff.service;

import com.glam.bff.dao.OutfitDAO;
import com.glam.bff.dto.outfit.BasicOutfitDTO;
import com.glam.bff.dto.outfit.OutfitDTO;
import com.glam.bff.mapper.outfit.OutfitDTOMapper;
import com.glam.bff.repository.UserOutfitRepository;
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
    private UserOutfitRepository userOutfitRepository;

    public List<OutfitDTO> getUserOutfits(String userId) {

        // get outfits from DB per userId
        List<OutfitDAO> outfitDAOList = userOutfitRepository.findOutfitsByUserId(userId);

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
        OutfitDAO savedOutfitDAO = userOutfitRepository.save(outfitDAO);

        // get outfit from DB
        OutfitDAO outfitDAOFromDB = userOutfitRepository.findById(savedOutfitDAO.getOutfitId()).get();
        return outfitDTOMapper.daoToDto(outfitDAOFromDB);

    }

    public void deleteUserOutfit(String outfitId, String userId) throws Exception {

        checkOutfitExistance(outfitId, userId);

        userOutfitRepository.deleteById(outfitId);

    }

    public OutfitDTO updateUserOutfit(String outfitId, String userId, BasicOutfitDTO outfitDTO) throws Exception {

        checkOutfitExistance(outfitId, userId);

        // DTO -> DAO
        OutfitDTOMapper outfitDTOMapper = applicationContext.getBean(OutfitDTOMapper.class);
        OutfitDAO newOutfitDAO = outfitDTOMapper.basicDtoToDao(outfitDTO);
        newOutfitDAO.setOutfitId(outfitId);
        newOutfitDAO.setUserId(userId);

        OutfitDAO outfitDAO = userOutfitRepository.save(newOutfitDAO);

        // get outfit from DB
        OutfitDAO savedOutfitDAO = userOutfitRepository.findById(outfitDAO.getOutfitId()).get();
        return outfitDTOMapper.daoToDto(savedOutfitDAO);

    }

    /////////////////////
    // PRIVATE METHODS //
    /////////////////////

    private OutfitDAO checkOutfitExistance (String outfitId, String userId) throws Exception {

        // get outfit from DB
        Optional<OutfitDAO> optionalOutfitDAO = userOutfitRepository.findById(outfitId);

        if(!optionalOutfitDAO.isPresent()){
            throw new Exception("No outfit present with ID: " + outfitId);
        }

        OutfitDAO outfitDAO = optionalOutfitDAO.get();

        if(!outfitDAO.getUserId().equalsIgnoreCase(userId)) {
            throw new Exception("No outfit present with ID: " + outfitId + " for user: " + userId);
        }

        return outfitDAO;

    }

    public OutfitDTO getUserOutfit(String outfitId, String userId) throws Exception {

        OutfitDAO outfitDAO = checkOutfitExistance(outfitId, userId);

        // DTO -> DAO
        OutfitDTOMapper outfitDTOMapper = applicationContext.getBean(OutfitDTOMapper.class);
        return outfitDTOMapper.daoToDto(outfitDAO);

    }
}
