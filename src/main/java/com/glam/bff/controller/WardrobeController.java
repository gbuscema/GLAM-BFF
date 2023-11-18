package com.glam.bff.controller;

import com.glam.bff.dto.garment.BasicGarmentDTO;
import com.glam.bff.dto.garment.GarmentDTO;
import com.glam.bff.dto.garment.GarmentPhotoDTO;
import com.glam.bff.dto.garment.enums.CategoryEnum;
import com.glam.bff.dto.wardrobe.WardrobeDTO;
import com.glam.bff.repository.UserOutfitRepository;
import com.glam.bff.repository.UserWardrobeRepository;
import com.glam.bff.service.WardrobeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.glam.bff.utils.Constants.*;

@Validated
@RestController
@RequestMapping("/wardrobes")
public class WardrobeController {

        @Autowired
        private WardrobeService wardrobeService;

        private final UserOutfitRepository userOutfitRepository;
        private final UserWardrobeRepository userWardrobeRepository;

        public WardrobeController(UserOutfitRepository userOutfitRepository,
                        UserWardrobeRepository userWardrobeRepository) {
                this.userOutfitRepository = userOutfitRepository;
                this.userWardrobeRepository = userWardrobeRepository;
        }

        @GetMapping("/users/{userId}")
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to get the user's wardrobe in GLAM", description = "Used to get the garment list inside the user's wardrobe")
        public WardrobeDTO getUserWardrobe(
                        @NotNull(message = "User ID cannot be null") @NotBlank(message = "User ID cannot be blank") @PathVariable(USER_ID) final String userId)
                        throws Exception {

                return wardrobeService.getUserWardrobe(userId);

        }

        @PostMapping("/users/{userId}/overwrite")
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to save the user's wardrobe in GLAM", description = "Used to save the garment list inside the user's wardrobe")
        public void saveUserWardrobe(
                        @NotNull(message = "User ID cannot be null") @NotBlank(message = "User ID cannot be blank") @PathVariable(USER_ID) final String userId,
                        @RequestBody List<BasicGarmentDTO> garmentList) throws Exception {

                wardrobeService.saveUserWardrobe(userId, garmentList);

        }

        @GetMapping("/users/{userId}/categories/{category}")
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to get the user's categories garments in GLAM", description = "Used to get the garment list inside the user's wardrobe by CATEGORY")
        public WardrobeDTO getUserWardrobeCategoriesGarments(
                        @NotNull(message = "User ID cannot be null") @NotBlank(message = "User ID cannot be blank") @PathVariable(USER_ID) final String userId,
                        @NotNull(message = "Category cannot be null") @PathVariable(CATEGORY) final CategoryEnum category)
                        throws Exception {

                return wardrobeService.getUserWardrobeCategoriesGarments(userId, category);

        }

        @PostMapping(value = "/users/{userId}/garments/complete/save", consumes = {
                        MediaType.MULTIPART_FORM_DATA_VALUE
        }, produces = { MediaType.APPLICATION_JSON_VALUE })
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to completely save an user's garment in GLAM", description = "Used to save the garment together with its photo")
        public GarmentDTO saveUserGarmentInWardrobeComplete(
                        @NotNull(message = "User ID cannot be null") @NotBlank(message = "User ID cannot be blank") @PathVariable(USER_ID) final String userId,
                        @NotNull(message = "Garment JSON String cannot be null") @NotBlank(message = "Garment JSON String cannot be blank") @RequestParam(value = "garment", required = false) String userGarment,
                        @NotNull(message = "Garment Photo file cannot be null") @RequestParam(value = "photo", required = false) MultipartFile photo)
                        throws Exception {

                return wardrobeService.saveUserGarmentInWardrobeComplete(userId, userGarment, photo);

        }

        @PostMapping("/users/{userId}/garments/save")
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to save an user's garment information in GLAM", description = "Used to save the garment's information")
        public GarmentDTO addUserGarmentInWardrobe(
                        @PathVariable(USER_ID) final String userId,
                        @RequestBody BasicGarmentDTO userGarment) throws Exception {

                return wardrobeService.saveUserGarmentInWardrobe(userId, userGarment);

        }

        @PostMapping(value = "/users/{userId}/garments/photo/save", consumes = {
                        MediaType.MULTIPART_FORM_DATA_VALUE
        }, produces = { MediaType.APPLICATION_JSON_VALUE })
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to save an user garment's photo in GLAM", description = "Used to save the garment's photo and retrieve the list of properties for the garment")
        public BasicGarmentDTO saveUserGarmentPhoto(
                        @PathVariable(USER_ID) final String userId,
                        @RequestParam(value = "photo", required = false) MultipartFile photo) throws Exception {

                return wardrobeService.saveUserGarmentPhoto(userId, photo);

        }

        @GetMapping("/users/{userId}/garments/photo/{garmentPhotoId}")
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to get the user garment's photo in GLAM", description = "Used to get the garment's photo")
        public ResponseEntity<byte[]> getGarmentPhoto(
                        @PathVariable(USER_ID) final String userId,
                        @PathVariable(GARMENT_PHOTO_ID) final String garmentPhotoId) throws Exception {

                GarmentPhotoDTO garmentPhotoDTO = wardrobeService.getGarmentPhoto(userId, garmentPhotoId);

                if (garmentPhotoDTO == null) {
                        return null;
                }

                HttpHeaders headers = new HttpHeaders();

                // set Media Type
                Path path = new File(garmentPhotoDTO.getFileName()).toPath();
                String mimeType;
                try {
                        mimeType = Files.probeContentType(path);
                        headers.add("Content-Type", mimeType);
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }

                return new ResponseEntity<>(garmentPhotoDTO.getPhoto(), headers, HttpStatus.OK);

        }

        @GetMapping("/users/{userId}/garments/{garmentId}")
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to get the user garment in GLAM", description = "Used to get the garment and its photo")
        public GarmentDTO getUserGarment(
                        @PathVariable(GARMENT_ID) final String garmentId,
                        @PathVariable(USER_ID) final String userId) throws Exception {

                return wardrobeService.getUserGarment(garmentId, userId);
        }

        @DeleteMapping("/users/{userId}/garments/{garmentId}")
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to delete the user garment in GLAM", description = "Used to delete the garment and its photo")
        public void deleteUserGarment(
                        @PathVariable(GARMENT_ID) final String garmentId,
                        @PathVariable(USER_ID) final String userId) throws Exception {

                wardrobeService.deleteUserGarment(garmentId, userId);
        }

        @PutMapping("/users/{userId}/garments/{garmentId}")
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to update the user garment in GLAM", description = "Used to update the garment")
        public GarmentDTO updateUserGarment(
                        @PathVariable(GARMENT_ID) final String garmentId,
                        @PathVariable(USER_ID) final String userId,
                        @RequestBody BasicGarmentDTO basicGarmentDTO) throws Exception {

                return wardrobeService.updateUserGarment(garmentId, userId, basicGarmentDTO);
        }

        @DeleteMapping("/users/{userId}/garments/photo/{garmentPhotoId}")
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to delete the user garment'photo in GLAM", description = "Used to delete the garment's photo")
        public void deleteUserGarmentPhoto(
                        @PathVariable(USER_ID) final String userId,
                        @PathVariable(GARMENT_PHOTO_ID) final String garmentPhotoId) throws Exception {

                wardrobeService.deleteUserGarmentPhoto(userId, garmentPhotoId);
        }

        @PutMapping(value = "/users/{userId}/garments/photo/{garmentPhotoId}", consumes = {
                        MediaType.MULTIPART_FORM_DATA_VALUE
        }, produces = { MediaType.APPLICATION_JSON_VALUE })
        @Tag(name = "Wardrobe")
        @Operation(summary = "API to update an user garment's photo in GLAM", description = "Used to update the garment's photo and retrieve the list of properties for the garment")
        public BasicGarmentDTO updateUserGarmentPhoto(
                        @PathVariable(USER_ID) final String userId,
                        @PathVariable(GARMENT_PHOTO_ID) final String garmentPhotoId,
                        @RequestParam(value = "photo", required = false) MultipartFile photo) throws Exception {

                return wardrobeService.updateUserGarmentPhoto(userId, garmentPhotoId, photo);

        }

        @PostMapping(value = "/users/{userId}/garments/mock")
        @Tag(name = "WardrobeDbCreation")
        @Operation(summary = "API that populates the database with mock data")
        public void uploadMockGarments(
                        @PathVariable(USER_ID) final String userId) throws Exception {

                wardrobeService.saveUserMockData(userId);

        }



}
