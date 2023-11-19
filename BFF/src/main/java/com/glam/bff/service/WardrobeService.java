package com.glam.bff.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.glam.bff.client.wardrobe.MockClient;
import com.glam.bff.client.wardrobe.WardrobeClient;
import com.glam.bff.dto.garment.enums.CategoryEnum;
import com.glam.bff.openapi.wardrobe.model.GarmentDAO;
import com.glam.bff.openapi.wardrobe.model.GarmentPhotoDAO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.glam.bff.dto.garment.BasicGarmentDTO;
import com.glam.bff.dto.garment.GarmentDTO;
import com.glam.bff.dto.garment.GarmentPhotoDTO;
import com.glam.bff.dto.wardrobe.WardrobeDTO;
import com.glam.bff.mapper.wardrobe.GarmentDTOMapper;
import com.glam.bff.mapper.wardrobe.GarmentPhotoDTOMapper;
import com.glam.bff.validators.MultipartPhoto;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Service
@Validated
public class WardrobeService {

    @Autowired
    private WardrobeClient wardrobeClient;
    @Autowired
    private MockClient mockClient;

    @Autowired
    private ApplicationContext applicationContext;


    /////////////////////
    // PRIVATE METHODS //
    /////////////////////

    private GarmentDAO saveGarment(String userId, GarmentDAO garmentDAO) {

        // TODO generate season and styles from OpenAI
        // TODO implement vectorDB save
        return wardrobeClient.addUserGarmentInWardrobe(userId, garmentDAO);

    }

    private void deleteGarment(String userId, String garmentId) {
        // delete the garment
        wardrobeClient.deleteUserGarment(garmentId, userId);

        // TODO implement delete on vector DB
    }

    private GarmentDAO checkGarmentExistance(String garmentId, String userId) throws Exception {

        // get garment from DB
        GarmentDAO garmentDAO = wardrobeClient.getUserGarment(garmentId, userId);

        if (garmentDAO == null) {
            throw new Exception("No garment present with ID: " + garmentId);
        }

        if (!garmentDAO.getUserId().equalsIgnoreCase(userId)) {
            throw new Exception("No garment present with ID: " + garmentId + " for user: " + userId);
        }

        return garmentDAO;

    }

    private BasicGarmentDTO saveGarmentPhoto(String userId, String userGarmentPhotoId, MultipartFile photo)
            throws IOException, InterruptedException {

        File convFile = new File(System.getProperty("java.io.tmpdir")+"/" + photo.getOriginalFilename());
        photo.transferTo(convFile);

        // Save Photo in DB
        GarmentDAO garmentDAO = null;
        if(userGarmentPhotoId == null){
            garmentDAO = wardrobeClient.saveUserGarmentPhoto(userId, convFile);
        } else {
            garmentDAO = wardrobeClient.updateUserGarmentPhoto(userId, userGarmentPhotoId, convFile);
        }

        if(garmentDAO == null) {
            return null;
        }

        // TODO Generate Texture

        // TODO Vision API call

        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        return garmentDTOMapper.basicDaoToDto(garmentDAO);

    }

    /////////////////////
    // PUBLIC METHODS //
    /////////////////////

    public GarmentDTO saveUserGarmentInWardrobeComplete(
            String userId,
            String userGarment,
            @MultipartPhoto MultipartFile photo) throws IOException {

        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + photo.getOriginalFilename());
        photo.transferTo(convFile);

        GarmentDAO garmentDAO = wardrobeClient.saveUserGarmentInWardrobeComplete(userId, userGarment, convFile);

        // Mapping DAO -> DTO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        return garmentDTOMapper.daoToDto(garmentDAO);

    }

    public WardrobeDTO getUserWardrobe(String userId) {

        // Mapping DAO -> DTO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        List<GarmentDAO> garmentDAOList = wardrobeClient.getUserWardrobe(userId);
        List<GarmentDTO> garmentDTOList = garmentDAOList.stream().map(garmentDTOMapper::daoToDto).toList();

        return new WardrobeDTO(userId, garmentDTOList);

    }

    public GarmentPhotoDTO getGarmentPhoto(String userId, String garmentPhotoId) throws IOException {

        GarmentPhotoDAO garmentPhotoDAO = wardrobeClient.getGarmentPhotoInfo(userId, garmentPhotoId);

        // Mapping DAO -> DTO
        GarmentPhotoDTOMapper garmentPhotoDTOMapper = applicationContext.getBean(GarmentPhotoDTOMapper.class);
        GarmentPhotoDTO garmentPhotoDTO = garmentPhotoDTOMapper.daoToDto(garmentPhotoDAO);

        if (garmentPhotoDTO.getUserId().equals(userId)) {
            return garmentPhotoDTO;
        } else {
            return null;
        }

    }

    public BasicGarmentDTO saveUserGarmentPhoto(String userId, MultipartFile photo)
            throws IOException, InterruptedException {

        return saveGarmentPhoto(userId, null, photo);

    }

    public GarmentDTO saveUserGarmentInWardrobe(String userId, BasicGarmentDTO userGarment) {

        // Mapping DTO -> DAO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        GarmentDAO garmentDAO = garmentDTOMapper.basicDtoToDao(userGarment);
        garmentDAO.setUserId(userId);

        // get photo from garment
        if (userGarment.getPhotoUri() != null && !userGarment.getPhotoUri().isEmpty()) {

            String[] uriArray = userGarment.getPhotoUri().split("/photo/");

            if (uriArray.length > 1) {

                String photoId = uriArray[1];

                GarmentPhotoDAO garmentPhotoDAO = wardrobeClient.getGarmentPhotoInfo(userId, photoId);
                garmentDAO.setPhoto(garmentPhotoDAO);

            }

        }

        // Save Garment in DB
        GarmentDAO savedGarment = saveGarment(userId, garmentDAO);

        // Mapping DAO -> DTO
        return garmentDTOMapper.daoToDto(savedGarment);

    }

    // service for mock data upload
    public void saveUserMockData(String userId) {

        mockClient.uploadMockGarments(userId);

    }

    public WardrobeDTO getUserWardrobeCategoriesGarments(String userId, CategoryEnum category) {

        // Mapping DAO -> DTO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        List<GarmentDAO> garmentDAOList = wardrobeClient.getUserWardrobeCategoriesGarments(userId, category.getValue().toUpperCase());
        List<GarmentDTO> garmentDTOList = garmentDAOList.stream().map(garmentDTOMapper::daoToDto).toList();

        return new WardrobeDTO(userId, garmentDTOList);

    }

    public void deleteUserGarment(String garmentId, String userId) throws Exception {

        GarmentDAO garmentDAO = checkGarmentExistance(garmentId, userId);

        // delete the garment photo
        GarmentPhotoDAO garmentPhotoDAO = garmentDAO.getPhoto();
        if (garmentPhotoDAO != null && garmentPhotoDAO.getPhotoId() != null) {
            String photoId = garmentPhotoDAO.getPhotoId();
            wardrobeClient.deleteUserGarmentPhoto(userId, photoId);
        }

        deleteGarment(userId, garmentId);

    }

    public GarmentDTO updateUserGarment(String garmentId, String userId, BasicGarmentDTO basicGarmentDTO)
            throws Exception {

        // DTO -> DAO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        GarmentDAO garmentDAO = garmentDTOMapper.basicDtoToDao(basicGarmentDTO);

        GarmentDAO updatedGarmentDAO = wardrobeClient.updateUserGarment(garmentId, userId, garmentDAO);

        // DAO -> DTO
        return garmentDTOMapper.daoToDto(updatedGarmentDAO);

    }

    public void deleteUserGarmentPhoto(String userId, String garmentPhotoId) throws Exception {

        wardrobeClient.deleteUserGarmentPhoto(userId, garmentPhotoId);

    }

    public BasicGarmentDTO updateUserGarmentPhoto(String userId, String garmentPhotoId, MultipartFile photo)
            throws IOException, InterruptedException {

        return saveGarmentPhoto(userId, garmentPhotoId, photo);

    }

    public GarmentDTO getUserGarment(String garmentId, String userId) throws Exception {

        GarmentDAO garmentDAO = checkGarmentExistance(garmentId, userId);

        // DAO -> DTO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        return garmentDTOMapper.daoToDto(garmentDAO);

    }

    public void saveUserWardrobe(String userId, List<BasicGarmentDTO> garmentList) {

        // DTO -> DAO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        List<GarmentDAO> garmentDAOList = garmentList
                .stream()
                .map(garmentDTOMapper::basicDtoToDao)
                .toList();

        wardrobeClient.saveUserWardrobe(userId, garmentDAOList);

        // TODO Update Vectorial DB

    }
}
