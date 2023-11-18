package com.glam.wardrobeservice.service;

import com.glam.wardrobeservice.dao.OutfitDAO;
import com.glam.wardrobeservice.repository.UserOutfitRepository;
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

    public List<OutfitDAO> getUserOutfits(String userId) {

        // get outfits from DB per userId
        return userOutfitRepository.findOutfitsByUserId(userId);

    }

    public OutfitDAO saveUserOutfit(String userId, OutfitDAO outfitDAO) {

        outfitDAO.setUserId(userId);

        // save user outfit inside DB
        return userOutfitRepository.save(outfitDAO);

    }

    public void deleteUserOutfit(String outfitId, String userId) throws Exception {

        checkOutfitExistance(outfitId, userId);

        userOutfitRepository.deleteById(outfitId);

    }

    public OutfitDAO updateUserOutfit(String outfitId, String userId, OutfitDAO newOutfitDAO) throws Exception {

        checkOutfitExistance(outfitId, userId);

        newOutfitDAO.setOutfitId(outfitId);
        newOutfitDAO.setUserId(userId);

        OutfitDAO outfitDAO = userOutfitRepository.save(newOutfitDAO);

        // get outfit from DB
        return userOutfitRepository.findById(outfitDAO.getOutfitId()).get();

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

    public OutfitDAO getUserOutfit(String outfitId, String userId) throws Exception {

        return checkOutfitExistance(outfitId, userId);

    }

    public OutfitDAO updateUserOutfitFavorite(String outfitId, String userId, Boolean isFavorite) throws Exception {

        OutfitDAO outfitDAO = checkOutfitExistance(outfitId, userId);

        outfitDAO.setIsFavorite(isFavorite);

        // update Outfit
        return userOutfitRepository.save(outfitDAO);

    }
}
