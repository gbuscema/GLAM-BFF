package com.glam.bff.service;

import com.glam.bff.client.polycam.PolycamClient;
import com.glam.bff.dao.GarmentDAO;
import com.glam.bff.dao.GarmentPhotoDAO;
import com.glam.bff.dao.SubcategoryDAO;
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
import com.glam.bff.repository.WardrobeVectoreStore;
import com.glam.bff.utils.DAORelevantDataUtils;
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
import java.util.List;
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
    private WardrobeVectoreStore wardrobeVectoreStore;
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

        ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, arg1, arg2);
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
                null
        );
        GarmentPhotoDAO savedGarmentPhotoDAO = garmentPhotoRepository.save(garmentPhotoDAO);

        GarmentDAO garmentDAO = new GarmentDAO();
        garmentDAO.setUserId(userId);
        garmentDAO.setPhoto(savedGarmentPhotoDAO);

        Resource resource = new ByteArrayResource(byteArr);

        // Vision API call
        var visionApiResponse = cloudVisionTemplate.analyzeImage(
                resource,
                Type.LABEL_DETECTION,
                Type.TEXT_DETECTION,
                Type.IMAGE_PROPERTIES,
                Type.OBJECT_LOCALIZATION);

        log.debug(visionApiResponse.getLabelAnnotationsList().toString());
        log.debug(visionApiResponse.getTextAnnotationsList().toString());
        log.debug(visionApiResponse.getLocalizedObjectAnnotationsList().toString());
        log.debug(visionApiResponse.getImagePropertiesAnnotation().getDominantColors().getColorsList().toString());

        // Mapping DAO -> DTO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        BasicGarmentDTO result = garmentDTOMapper.visionApiToBasicDto(visionApiResponse);
        StringBuilder colors = new StringBuilder();
        visionApiResponse.getImagePropertiesAnnotation().getDominantColors().getColorsList().forEach(colorInfo -> {
            colors.append(colorInfo.getColor()).append("\n");
        });
        String prompt = "Generate a textile texture based on those info: \n" +
                "Garment type: " + result.getCategory() + "\n" +
                "Garment colors: \n" + colors.toString() +
                "Garment pattern: " + result.getPattern() + "\n" +
                "Garment fabric: " + result.getFabric() + "\n" +
                "Garment sub category: " + result.getSubCategory() + "\n";
        ResponseDTO polycamResponse = PolycamClient.fetchRemoteData(prompt);
        String textureUrl = polycamResponse.getImages().getTexture();
//        String svgId = DAORelevantDataUtils.getSvgId(result.getSubCategory());
//        Optional<SubcategoryDAO> subcategoryDAO = subcategoryRepository.findById(svgId);
//        //TODO Fillare l'svg con il textureUrl
//        String svg = subcategoryDAO.get().getSvg();
        result.setTextureUri(textureUrl);
        return result;

    }

    /////////////////////
    // PRIVATE METHODS //
    /////////////////////

    public GarmentDTO saveUserGarmentInWardrobeComplete(
            String userId,
            String userGarment,
            @MultipartPhoto MultipartFile photo) throws IOException {

        GarmentDTO garmentDTO = GarmentDTO.getJson(userGarment);
        // 10/11/2023, l'svg non serve più, sad story
//        String svgId = DAORelevantDataUtils.getSvgId(garmentDTO.getSubCategory());
//        Optional<SubcategoryDAO> subcategoryDAO = subcategoryRepository.findById(svgId);
//        garmentDTO.setTextureUri(subcategoryDAO.get().getSvg());

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

    private GarmentDAO saveGarment(GarmentDAO garmentDAO) {
        GarmentDAO savedGarment = userWardrobeRepository.save(garmentDAO);
        wardrobeVectoreStore.save(savedGarment);
        return savedGarment;
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

        // delete the garment
        userWardrobeRepository.deleteById(garmentId);

    }

    public GarmentDTO updateUserGarment(String garmentId, String userId, BasicGarmentDTO basicGarmentDTO)
            throws Exception {

        checkGarmentExistance(garmentId, userId);

        // Garment update DAO
        GarmentDTOMapper garmentDTOMapper = applicationContext.getBean(GarmentDTOMapper.class);
        GarmentDAO garmentDAO = garmentDTOMapper.basicDtoToDao(basicGarmentDTO);
        garmentDAO.setGarmentId(garmentId);
        garmentDAO.setUserId(userId);

        GarmentDAO updatedGarmentDAO = userWardrobeRepository.save(garmentDAO);

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
}
