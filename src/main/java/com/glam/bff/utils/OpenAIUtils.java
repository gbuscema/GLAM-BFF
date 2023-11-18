package com.glam.bff.utils;

import com.glam.bff.dto.garment.enums.CategoryEnum;
import com.glam.bff.dto.garment.enums.ColorEnum;
import com.glam.bff.dto.garment.enums.FabricEnum;
import com.glam.bff.dto.garment.enums.LayoutEnum;
import com.glam.bff.dto.garment.enums.SubCategoryEnum;
import com.glam.bff.model.Match;
import com.glam.bff.model.Wardrobe;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class OpenAIUtils {

    public static String getColumnConstraintWardrobeSentence(Wardrobe wardrobe) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create me 10 garments. ");
        // Extract map
//        Map<String, Set<String>> wardrobeMap = wardrobe.convertWardrobeIntoMap();
//
//        if(!wardrobeMap.keySet().isEmpty()){
//
//            stringBuilder.append(" Make me an outfit from this info: ");
//            stringBuilder.append(wardrobeMap.keySet());
//            stringBuilder.append(". ");
//
//        }
//
//        for (Map.Entry<String, Set<String>> entry : wardrobeMap.entrySet()) {
//
//            String column = entry.getKey();
//            Set<String> set = entry.getValue();
//
//            stringBuilder.append("For '");
//            stringBuilder.append(column);
//            stringBuilder.append("' allowed values are: ");
//            stringBuilder.append(set);
//            stringBuilder.append(". ");
//
//        }
        stringBuilder.append("{responseFormat}");
        return stringBuilder.toString();
    }

    public static String getOutfitSuggestionPrompt(List<Match> garments, List<String> relevants, LayoutEnum layout) {

        Set<String> maincolors = garments.stream().map(g -> g.getMainColor()).collect(Collectors.toSet());
        Set<String> subCategories = garments.stream().map(g -> g.getSubCategory()).collect(Collectors.toSet());
        Set<String> fabrics = garments.stream().map(g-> g.getFabric()).collect(Collectors.toSet());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{userPrompt}");
        stringBuilder.append("Make me an outfit from this info: ");
        stringBuilder.append(relevants);
        stringBuilder.append(". I want an outfit made of ");

        if(LayoutEnum.ONE_PIECE.equals(layout)){
            stringBuilder.append("a single one-piece garmet and a pair of shoes if I have one in my wardrobe");
        }else{
            stringBuilder.append("at least 1 garment for body, 1 for legs, a cap if I have one in my wardrobe and a pair of shoes if I have one in my wardrobe. ");
        }

        stringBuilder.append("{myStyles}");


        stringBuilder.append("For 'fabric' consider the following values: ").append(Arrays.toString(FabricEnum.values())).append(". ");
        stringBuilder.append("For 'mainColor' consider the following values: ").append(Arrays.toString(ColorEnum.values())).append(". ");
        stringBuilder.append("For 'category' consider the following values: ").append(Arrays.toString(CategoryEnum.values())).append(". ");
        stringBuilder.append("For 'subCategory' consider the following values: ").append(Arrays.toString(SubCategoryEnum.values())).append(". ");


//        stringBuilder.append("In my wardrobe I have: ");
//        for(Match match : garments){
//            stringBuilder.append("a ");
//            stringBuilder.append(match.getMainColor());
//            stringBuilder.append(" ");
//            stringBuilder.append(match.getSubCategory());
//            stringBuilder.append(" of ");
//            stringBuilder.append(match.getFabric());
//            stringBuilder.append(", ");
//        }

//        for(Match match : garments){
//            stringBuilder.append("a ");
//            stringBuilder.append(match.getDescription().replace(".",""));
//
//            if(!match.getDescription().toLowerCase().contains(match.getMainColor().toLowerCase())){
//                stringBuilder.append(" ");
//                stringBuilder.append(match.getMainColor());
//            }
//
//            if(!match.getDescription().toLowerCase().contains(match.getFabric().toLowerCase())){
//                stringBuilder.append(" of ");
//                stringBuilder.append(match.getFabric());
//            }
//            stringBuilder.append(", ");
//        }


        stringBuilder.append("{locationInfo}");
        stringBuilder.append("Respect all the uppercase of this message in your response. ");
        stringBuilder.append("{responseFormat}");
        return stringBuilder.toString().replace(", {locationInfo}", ". {locationInfo}");
    }

    public static String getLocationInfo(Object location, String date){
        if(location == null || StringUtils.isBlank(location.toString())){
            return  StringUtils.EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder("Make the outfit considering I will be in ");
        stringBuilder.append(location);
        stringBuilder.append(" so consider the weather of this location for ");

        if(StringUtils.isBlank(date)){
            stringBuilder.append("today");
        }else{
            stringBuilder.append(date);
        }

        stringBuilder.append(". ");

        return stringBuilder.toString();
    }

    public static String getMyStylesInfo(Object styles){
        if(styles == null || styles.toString().isBlank()){
            return StringUtils.EMPTY;
        }

        return "I usually wear " + styles.toString().replaceAll(",", " or ") +". ";
    }

    public static String getUserPromptPhrase(Object userPrompt){
        if(userPrompt == null || userPrompt.toString().isBlank()){
            return StringUtils.EMPTY;
        }

        return "Starting from this sentence I'm telling you, translated in english: \"" + userPrompt +"\". ";
    }
}
