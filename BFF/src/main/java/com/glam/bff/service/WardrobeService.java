package com.glam.bff.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import com.glam.bff.dto.polycam.StableDiffusionResponseDTO;
import com.glam.bff.threads.AnalyzeImageThread;
import com.glam.bff.threads.UploadImageThread;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glam.bff.client.polycam.PolycamClient;
import com.glam.bff.dao.GarmentDAO;
import com.glam.bff.dao.GarmentPhotoDAO;
import com.glam.bff.dto.garment.BasicGarmentDTO;
import com.glam.bff.dto.garment.GarmentDTO;
import com.glam.bff.dto.garment.GarmentPhotoDTO;
import com.glam.bff.dto.garment.enums.CategoryEnum;
import com.glam.bff.dto.polycam.ResponseDTO;
import com.glam.bff.dto.wardrobe.WardrobeDTO;
import com.glam.bff.mapper.wardrobe.GarmentDTOMapper;
import com.glam.bff.mapper.wardrobe.GarmentPhotoDTOMapper;
import com.glam.bff.repository.GarmentPhotoRepository;
import com.glam.bff.repository.SubcategoryRepository;
import com.glam.bff.repository.UserWardrobeRepository;
import com.glam.bff.utils.WardrobeVectorStoreBuilder;
import com.glam.bff.validators.MultipartPhoto;
import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vision.v1.Feature.Type;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
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
    private WardrobeVectorStoreBuilder wardrobeVectorStoreBuilder;
    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

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

    private BasicGarmentDTO saveGarmentPhoto(String userId, String userGarmentPhotoId, MultipartFile photo)
            throws IOException, InterruptedException {

        // convert multipart into byte[]
        byte[] byteArr = photo.getBytes();
        // InputStream inputStream = new ByteArrayInputStream(byteArr);
        ByteArrayInputStream bais = new ByteArrayInputStream(byteArr);
        BufferedImage image = ImageIO.read(bais);
        File outputFile = new File("input-temp.png");
        ImageIO.write(image, "png", outputFile);
        String pythonScriptPath = "src/main/resources/python/BackgroundRemover.py";
        String arg1 = "input-temp.png";
        String arg2 = "out-temp.png";

        ProcessBuilder processBuilder = new ProcessBuilder("python3", pythonScriptPath, arg1, arg2);
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        // process.getErrorStream().transferTo(System.err);
        // process.getInputStream().transferTo(System.out);
        process.waitFor();
        try (InputStream inputStream = new FileInputStream("out-temp.png")) {
            byteArr = inputStream.readAllBytes();
        }

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

        // Resource resource = new ByteArrayResource(byteArr);

        // Generate Texture
        UploadImageThread uploadImageThread = new UploadImageThread(this);
        Thread uploadImageT = new Thread(uploadImageThread);

        // Vision API call
        AnalyzeImageThread analyzeImageThread = new AnalyzeImageThread(cloudVisionTemplate, photo.getResource());
        Thread analyzeImageT = new Thread(analyzeImageThread);

        uploadImageT.start();
        analyzeImageT.start();
        uploadImageT.join();
        analyzeImageT.join();

        AnnotateImageResponse visionApiResponse = analyzeImageThread.getValue();
        log.debug(visionApiResponse.getLabelAnnotationsList().toString());

        // Mapping DAO -> DTO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        BasicGarmentDTO result = garmentDTOMapper.visionApiToBasicDto(visionApiResponse);

        String textureUrl = uploadImageThread.getTextureUrl();
        result.setTextureUri(textureUrl);

        if (savedGarmentPhotoDAO != null) {
            result.setPhotoUri("/wardrobes/users/" + savedGarmentPhotoDAO.getUserId() + "/garments/photo/"
                    + savedGarmentPhotoDAO.getPhotoId());
        }

        return result;

    }

    /////////////////////
    // PRIVATE METHODS //
    /////////////////////

    public String uploadImage(File file) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<StableDiffusionResponseDTO> response = restTemplate.postForEntity(
                "https://5918-87-10-241-96.ngrok-free.app/upload", requestEntity, StableDiffusionResponseDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(response.getBody()).getTextureUri();
        } else {
            return "";
        }
    }

    public GarmentDTO saveUserGarmentInWardrobeComplete(
            String userId,
            String userGarment,
            @MultipartPhoto MultipartFile photo) throws IOException {

        GarmentDTO garmentDTO = GarmentDTO.getJson(userGarment);
        // 10/11/2023, l'svg non serve più, sad story
        // String svgId = DAORelevantDataUtils.getSvgId(garmentDTO.getSubCategory());
        // Optional<SubcategoryDAO> subcategoryDAO =
        // subcategoryRepository.findById(svgId);
        // garmentDTO.setTextureUri(subcategoryDAO.get().getSvg());

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

        // Mapping DTO -> DAO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        GarmentDAO garmentDAO = garmentDTOMapper.basicDtoToDao(garmentDTO);
        garmentDAO.setUserId(userId);
        garmentDAO.setPhoto(savedGarmentPhotoDAO);

        // Save Garment in DB
        GarmentDAO savedGarment = saveGarment(garmentDAO);

        // update Photo in DB
        garmentPhotoDAO.setGarment(savedGarment);
        garmentPhotoRepository.save(garmentPhotoDAO);

        // Mapping DAO -> DTO
        return garmentDTOMapper.daoToDto(savedGarment);

    }

    public WardrobeDTO getUserWardrobe(String userId) {

        // Mapping DAO -> DTO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        List<GarmentDAO> garmentDAOList = userWardrobeRepository.findGarmentsByUserId(userId);
        List<GarmentDTO> garmentDTOList = garmentDAOList.stream().map(garmentDTOMapper::daoToDto).toList();

        return new WardrobeDTO(userId, garmentDTOList);

    }

    public GarmentPhotoDTO getGarmentPhoto(String userId, String garmentPhotoId) {

        GarmentPhotoDAO garmentPhotoDAO = garmentPhotoRepository.findById(garmentPhotoId).get();

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

                GarmentPhotoDAO garmentPhotoDAO = garmentPhotoRepository.findById(photoId).get();
                garmentDAO.setPhoto(garmentPhotoDAO);

            }

        }

        // Save Garment in DB
        GarmentDAO savedGarment = saveGarment(garmentDAO);

        // Mapping DAO -> DTO
        return garmentDTOMapper.daoToDto(savedGarment);

    }

    // service for mock data upload
    public void saveUserMockData(String userId) {

        try {

            String mockJsonPath = "src/main/resources/mockup/mockWardrobeComplete.json";
            String mockImagesFolder = "src/main/resources/mockup/images/";

            ObjectMapper mapper = new ObjectMapper();
            GarmentDTO[] garments = mapper
                    .readValue(new File(mockJsonPath), GarmentDTO[].class);
            for (int i = 0; i < garments.length; i++) {
                var garment = garments[i];
                Path path = Paths.get(
                        mockImagesFolder,
                        garment.getTitle().replace(" ", "") + ".jpg");
                String originalFileName = garment.getTitle() + ".jpg";

                GarmentPhotoDAO garmentPhotoDAO = new GarmentPhotoDAO(
                        null,
                        null,
                        userId,
                        originalFileName,
                        Files.readAllBytes(path),
                        null);
                GarmentPhotoDAO savedGarmentPhotoDAO = garmentPhotoRepository.save(garmentPhotoDAO);
                GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
                GarmentDAO garmentDAO = garmentDTOMapper.basicDtoToDao(garment);
                garmentDAO.setPhoto(savedGarmentPhotoDAO);
                garmentDAO.setUserId(userId);
                var saved = saveGarment(garmentDAO);
                log.debug("saved:" + saved.getGarmentId());
            }

        } catch (Exception e) {
            log.debug(userId, e);
        }

    }

    public WardrobeDTO getUserWardrobeCategoriesGarments(String userId, CategoryEnum category) {

        // Mapping DAO -> DTO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        List<GarmentDAO> garmentDAOList = userWardrobeRepository.findGarmentsByUserIdAndCategory(userId,
                category.getValue().toUpperCase());
        List<GarmentDTO> garmentDTOList = garmentDAOList.stream().map(garmentDTOMapper::daoToDto).toList();

        return new WardrobeDTO(userId, garmentDTOList);

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

    public GarmentDTO updateUserGarment(String garmentId, String userId, BasicGarmentDTO basicGarmentDTO)
            throws Exception {

        checkGarmentExistance(garmentId, userId);

        // Garment update DAO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        GarmentDAO garmentDAO = garmentDTOMapper.basicDtoToDao(basicGarmentDTO);
        garmentDAO.setGarmentId(garmentId);
        garmentDAO.setUserId(userId);

        Optional<GarmentDAO> existingDAO = userWardrobeRepository.findById(garmentId);
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
        });

        GarmentDAO updatedGarmentDAO = updateGarment(garmentDAO);

        // DAO -> DTO
        return garmentDTOMapper.daoToDto(updatedGarmentDAO);

    }

    public void deleteUserGarmentPhoto(String userId, String garmentPhotoId) throws Exception {

        GarmentPhotoDAO garmentPhotoDAO = checkGarmentPhotoExistance(userId, garmentPhotoId);

        // delete photo from DB
        garmentPhotoRepository.deleteById(garmentPhotoId);

        // update garment from DB
        if (garmentPhotoDAO.getGarment() != null) {

            GarmentDAO garmentDAO = garmentPhotoDAO.getGarment();
            garmentDAO.setPhoto(null);
            userWardrobeRepository.save(garmentDAO);

        }
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

    private GarmentDAO saveGarment(GarmentDAO garmentDAO) {
        // TODO generate season and styles from OpenAI
        GarmentDAO savedGarment = userWardrobeRepository.save(garmentDAO);
        wardrobeVectorStoreBuilder.getWardrobeVectorStore().save(savedGarment);
        return savedGarment;
    }

    private void deleteGarment(String garmentId) {
        // delete the garment
        userWardrobeRepository.deleteById(garmentId);
        wardrobeVectorStoreBuilder.getWardrobeVectorStore().delete(garmentId);
    }

    private GarmentDAO updateGarment(GarmentDAO garmentDAO) {
        GarmentDAO updatedGarment = userWardrobeRepository.save(garmentDAO);
        wardrobeVectorStoreBuilder.getWardrobeVectorStore().update(garmentDAO);
        return updatedGarment;
    }

    public void saveUserWardrobe(String userId, List<BasicGarmentDTO> garmentList) {

        // DTO -> DAO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        List<GarmentDAO> garmentDAOList = garmentList
                .stream()
                .map(garmentDTOMapper::basicDtoToDao)
                .toList();

        // delete old entries
        List<GarmentDAO> oldUserGarments = userWardrobeRepository.findGarmentsByUserId(userId);
        List<GarmentPhotoDAO> oldUserGarmentsPhoto = oldUserGarments
                .stream()
                .map(GarmentDAO::getPhoto)
                .filter(Objects::nonNull)
                .toList();

        garmentPhotoRepository.deleteAll(oldUserGarmentsPhoto);
        oldUserGarments.forEach(garmentDAO -> {
            deleteGarment(garmentDAO.getGarmentId());
        });

        // create new entries
        garmentDAOList.forEach(garmentDAO -> {
            garmentDAO.setUserId(userId);
            saveGarment(garmentDAO);
        });

        // Update Vectorial DB

    }
}
