package com.glam.bff.controller;

import com.glam.bff.dto.outfit.BasicOutfitDTO;
import com.glam.bff.dto.outfit.OutfitDTO;
import com.glam.bff.service.OutfitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.glam.bff.utils.Constants.OUTFIT_ID;
import static com.glam.bff.utils.Constants.USER_ID;

@RestController
@RequestMapping("/outfits")
public class OutfitController {

    @Autowired
    private OutfitService outfitService;

    /**
     * Retrieve the list of outfits
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("/users/{userId}")
    @Tag(name = "Outfit")
    @Operation(
            summary = "API to get the user outfits in GLAM",
            description =
                    "Used to get the user's outfits")
    public List<OutfitDTO> getUserOutfits(
            @PathVariable(USER_ID)
            final String userId) throws Exception {

        return outfitService.getUserOutfits(userId);

    }

    @PostMapping("/users/{userId}/save")
    @Tag(name = "Outfit")
    @Operation(
            summary = "API to save the user outfit in GLAM",
            description =
                    "Used to save the user's outfit")
    public OutfitDTO saveUserOutfit(
            @PathVariable(USER_ID)
            final String userId,
            @RequestBody BasicOutfitDTO userOutfit) throws Exception {

        return outfitService.saveUserOutfit(userId, userOutfit);
    }

    @DeleteMapping("/{outfitId}/users/{userId}")
    @Tag(name = "Outfit")
    @Operation(
            summary = "API to delete the user's outfit in GLAM",
            description =
                    "Used to delete the user's outfit")
    public void deleteUserOutfit(
            @PathVariable(OUTFIT_ID)
            final String outfitId,
            @PathVariable(USER_ID)
            final String userId) throws Exception {

        outfitService.deleteUserOutfit(outfitId, userId);
    }

    @PutMapping("/{outfitId}/users/{userId}")
    @Tag(name = "Outfit")
    @Operation(
            summary = "API to update the user's outfit in GLAM",
            description =
                    "Used to update the user's outfit")
    public OutfitDTO updateUserOutfit(
            @PathVariable(OUTFIT_ID)
            final String outfitId,
            @PathVariable(USER_ID)
            final String userId,
            @RequestBody BasicOutfitDTO outfitDTO) throws Exception {

        return outfitService.updateUserOutfit(outfitId, userId, outfitDTO);

    }

    @GetMapping("/{outfitId}/users/{userId}")
    @Tag(name = "Outfit")
    @Operation(
            summary = "API to get the user's outfit in GLAM",
            description =
                    "Used to get the user's outfit")
    public OutfitDTO getUserOutfit(
            @PathVariable(OUTFIT_ID)
            final String outfitId,
            @PathVariable(USER_ID)
            final String userId) throws Exception {

        return outfitService.getUserOutfit(outfitId, userId);

    }

}
