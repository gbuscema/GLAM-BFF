package com.glam.wardrobeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glam.wardrobeservice.dao.GarmentDAO;
import com.glam.wardrobeservice.dao.GarmentPhotoDAO;
import com.glam.wardrobeservice.dao.enums.CategoryEnum;
import com.glam.wardrobeservice.repository.GarmentPhotoRepository;
import com.glam.wardrobeservice.repository.SubcategoryRepository;
import com.glam.wardrobeservice.repository.UserWardrobeRepository;
import com.glam.wardrobeservice.validators.MultipartPhoto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Validated
public class WardrobeService {

    @Autowired
    private UserWardrobeRepository userWardrobeRepository;
    @Autowired
    private GarmentPhotoRepository garmentPhotoRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    /////////////////////
    // PRIVATE METHODS //
    /////////////////////

    private GarmentDAO checkGarmentExistance(String garmentId, String userId) throws Exception {

        // get garment from DB
        Optional<GarmentDAO> optionalGarmentDAO = userWardrobeRepository.findById(garmentId);

        if (!optionalGarmentDAO.isPresent()) {
            throw new Exception("No garment present with ID: " + garmentId);
        }

        GarmentDAO garmentDAO = optionalGarmentDAO.get();

        if (!garmentDAO.getUserId().equalsIgnoreCase(userId)) {
            throw new Exception("No garment present with ID: " + garmentId + " for user: " + userId);
        }

        return garmentDAO;

    }

    private GarmentPhotoDAO checkGarmentPhotoExistance(String userId, String garmentPhotoId) throws Exception {

        // get garment from DB
        Optional<GarmentPhotoDAO> optionalGarmentPhotoDAO = garmentPhotoRepository.findById(garmentPhotoId);

        if (!optionalGarmentPhotoDAO.isPresent()) {
            throw new Exception("No garment photo present with ID: " + garmentPhotoId);
        }

        GarmentPhotoDAO garmentPhotoDAO = optionalGarmentPhotoDAO.get();

        if (!garmentPhotoDAO.getUserId().equalsIgnoreCase(userId)) {
            throw new Exception("No garment photo present with ID: " + garmentPhotoId + " for user: " + userId);
        }

        return garmentPhotoDAO;

    }

    private GarmentDAO saveGarmentPhoto(String userId, String userGarmentPhotoId, MultipartFile photo)
            throws IOException, InterruptedException {

        // convert multipart into byte[]
        byte[] byteArr = photo.getBytes();

        // Save Photo in DB
        GarmentPhotoDAO garmentPhotoDAO = new GarmentPhotoDAO(
                userGarmentPhotoId,
                null,
                userId,
                photo.getOriginalFilename(),
                byteArr,
                null);
        GarmentPhotoDAO savedGarmentPhotoDAO = garmentPhotoRepository.save(garmentPhotoDAO);

        GarmentDAO garmentDAO = new GarmentDAO();
        garmentDAO.setUserId(userId);
        garmentDAO.setPhoto(savedGarmentPhotoDAO);
        garmentDAO.setPhotoUri("/wardrobes/users/" + savedGarmentPhotoDAO.getUserId() + "/garments/photo/"
                + savedGarmentPhotoDAO.getPhotoId());

        return userWardrobeRepository.save(garmentDAO);

    }

    /////////////////////
    // PRIVATE METHODS //
    /////////////////////

    public GarmentDAO saveUserGarmentInWardrobeComplete(
            String userId,
            String userGarment,
            @MultipartPhoto MultipartFile photo) throws IOException {

        GarmentDAO garmentDAO = GarmentDAO.getJson(userGarment);

        // convert multipart into byte[]
        byte[] byteArr = photo.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArr);

        // Save Photo in DB
        GarmentPhotoDAO garmentPhotoDAO = new GarmentPhotoDAO(
                null,
                null,
                userId,
                photo.getOriginalFilename(),
                inputStream.readAllBytes(),
                null);
        GarmentPhotoDAO savedGarmentPhotoDAO = garmentPhotoRepository.save(garmentPhotoDAO);

        garmentDAO.setUserId(userId);
        garmentDAO.setPhoto(savedGarmentPhotoDAO);

        // Save Garment in DB
        GarmentDAO savedGarment = userWardrobeRepository.save(garmentDAO);

        // update Photo in DB
        garmentPhotoDAO.setGarmentId(savedGarment.getGarmentId());
        garmentPhotoRepository.save(garmentPhotoDAO);

        return savedGarment;

    }

    public List<GarmentDAO> getUserWardrobe(String userId) {

        return userWardrobeRepository.findGarmentsByUserId(userId);

    }

    public GarmentPhotoDAO getGarmentPhoto(String userId, String garmentPhotoId) {

        GarmentPhotoDAO garmentPhotoDAO = garmentPhotoRepository.findById(garmentPhotoId).get();

        if (garmentPhotoDAO.getUserId().equals(userId)) {
            return garmentPhotoDAO;
        } else {
            return null;
        }

    }

    public GarmentDAO saveUserGarmentPhoto(String userId, MultipartFile photo)
            throws IOException, InterruptedException {

        return saveGarmentPhoto(userId, null, photo);

    }

    public GarmentDAO saveUserGarmentInWardrobe(String userId, GarmentDAO userGarment) {

        userGarment.setUserId(userId);

        // get photo from garment
        if (userGarment.getPhotoUri() != null && !userGarment.getPhotoUri().isEmpty()) {

            String[] uriArray = userGarment.getPhotoUri().split("/photo/");

            if (uriArray.length > 1) {

                String photoId = uriArray[1];

                GarmentPhotoDAO garmentPhotoDAO = garmentPhotoRepository.findById(photoId).get();
                userGarment.setPhoto(garmentPhotoDAO);

            }

        }

        // Save Garment in DB
        return saveGarment(userGarment);

    }

    // service for mock data upload
    public void saveUserMockData(String userId) {

        try {

            String mockJsonPath = "src/main/resources/mockup/mockWardrobeComplete.json";
            String mockImagesFolder = "src/main/resources/mockup/images/";

            ObjectMapper mapper = new ObjectMapper();
            GarmentDAO[] garments = mapper
                    .readValue(new File(mockJsonPath), GarmentDAO[].class);
            for (int i = 0; i < garments.length; i++) {
                GarmentDAO garmentDAO = garments[i];
                Path path = Paths.get(
                        mockImagesFolder,
                        garmentDAO.getTitle().replace(" ", "") + ".jpg");
                String originalFileName = garmentDAO.getTitle() + ".jpg";

                GarmentPhotoDAO garmentPhotoDAO = new GarmentPhotoDAO(
                        null,
                        null,
                        userId,
                        originalFileName,
                        Files.readAllBytes(path),
                        null);
                GarmentPhotoDAO savedGarmentPhotoDAO = garmentPhotoRepository.save(garmentPhotoDAO);

                garmentDAO.setPhoto(savedGarmentPhotoDAO);
                garmentDAO.setUserId(userId);
                var saved = saveGarment(garmentDAO);
                log.debug("saved:" + saved.getGarmentId());
            }

        } catch (Exception e) {
            log.debug(userId, e);
        }

    }

    public List<GarmentDAO> getUserWardrobeCategoriesGarments(String userId, CategoryEnum category) {

        return userWardrobeRepository.findGarmentsByUserIdAndCategory(userId,
                category.getValue().toUpperCase());

    }

    public void deleteUserGarment(String garmentId, String userId) throws Exception {

        GarmentDAO garmentDAO = checkGarmentExistance(garmentId, userId);

        // delete the garment photo
        GarmentPhotoDAO garmentPhotoDAO = garmentDAO.getPhoto();
        if (garmentPhotoDAO != null && garmentPhotoDAO.getPhotoId() != null) {
            String photoId = garmentPhotoDAO.getPhotoId();
            garmentPhotoRepository.deleteById(photoId);
        }

        deleteGarment(garmentId);

    }

    public GarmentDAO updateUserGarment(String garmentId, String userId, GarmentDAO garmentDAO)
            throws Exception {

        checkGarmentExistance(garmentId, userId);

        // Garment update DAO
        garmentDAO.setGarmentId(garmentId);
        garmentDAO.setUserId(userId);

        /*Optional<GarmentDAO> existingDAO = userWardrobeRepository.findById(garmentId);
        existingDAO.ifPresent(dao -> {
            garmentDAO.setPhoto(dao.getPhoto());
            garmentDAO.setCategory(dao.getCategory());
            garmentDAO.setSubCategory(dao.getSubCategory());
            garmentDAO.setFabric(dao.getFabric());
            garmentDAO.setPattern(dao.getPattern());
            garmentDAO.setMainColor(dao.getMainColor());
            garmentDAO.setSecondColor(dao.getSecondColor());
            garmentDAO.setBrand(dao.getBrand());
            garmentDAO.setTitle(dao.getTitle());
            garmentDAO.setSeason(dao.getSeason());
            garmentDAO.setStyles(dao.getStyles());
            garmentDAO.setFabric(dao.getFabric());
        });*/

        return updateGarment(garmentDAO);

    }

    public void deleteUserGarmentPhoto(String userId, String garmentPhotoId) throws Exception {

        GarmentPhotoDAO garmentPhotoDAO = checkGarmentPhotoExistance(userId, garmentPhotoId);

        // delete photo from DB
        garmentPhotoRepository.deleteById(garmentPhotoId);

        // update garment from DB
        if (garmentPhotoDAO.getGarmentId() != null) {

            String garmentId = garmentPhotoDAO.getGarmentId();
            Optional<GarmentDAO> optionalGarmentDAO = userWardrobeRepository.findById(garmentId);
            if(optionalGarmentDAO.isPresent()){
                GarmentDAO garmentDAO = optionalGarmentDAO.get();
                garmentDAO.setPhoto(null);
                userWardrobeRepository.save(garmentDAO);
            }

        }
    }

    public GarmentDAO updateUserGarmentPhoto(String userId, String garmentPhotoId, MultipartFile photo)
            throws IOException, InterruptedException {

        return saveGarmentPhoto(userId, garmentPhotoId, photo);

    }

    public GarmentDAO getUserGarment(String garmentId, String userId) throws Exception {

        return checkGarmentExistance(garmentId, userId);

    }

    private GarmentDAO saveGarment(GarmentDAO garmentDAO) {

        return userWardrobeRepository.save(garmentDAO);
    }

    private void deleteGarment(String garmentId) {
        // delete the garment
        userWardrobeRepository.deleteById(garmentId);
    }

    private GarmentDAO updateGarment(GarmentDAO garmentDAO) {
        return userWardrobeRepository.save(garmentDAO);
    }

    public void saveUserWardrobe(String userId, List<GarmentDAO> garmentDAOList) {

        // delete old entries
        List<GarmentDAO> oldUserGarments = userWardrobeRepository.findGarmentsByUserId(userId);
        List<GarmentPhotoDAO> oldUserGarmentsPhoto = oldUserGarments
                .stream()
                .map(GarmentDAO::getPhoto)
                .filter(Objects::nonNull)
                .toList();

        garmentPhotoRepository.deleteAll(oldUserGarmentsPhoto);
        oldUserGarments.forEach(garmentDAO -> deleteGarment(garmentDAO.getGarmentId()));

        // create new entries
        garmentDAOList.forEach(garmentDAO -> {
            garmentDAO.setUserId(userId);
            saveGarment(garmentDAO);
        });

    }
}
