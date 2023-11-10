package com.glam.bff.utils;

import com.glam.bff.dao.GarmentDAO;
import com.glam.bff.dto.garment.enums.LayoutEnum;
import com.glam.bff.model.Match;
import com.glam.bff.model.Wardrobe;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class OpenAIUtils {

    public static String getColumnConstraintWardrobeSentence(Wardrobe wardrobe) {

        StringBuilder stringBuilder = new StringBuilder();
        // Extract map
        Map<String, Set<String>> wardrobeMap = wardrobe.convertWardrobeIntoMap();

        if(!wardrobeMap.keySet().isEmpty()){

            stringBuilder.append(" Make me an outfit from this info: ");
            stringBuilder.append(wardrobeMap.keySet());
            stringBuilder.append(". ");

        }

        for (Map.Entry<String, Set<String>> entry : wardrobeMap.entrySet()) {

            String column = entry.getKey();
            Set<String> set = entry.getValue();

            stringBuilder.append("For '");
            stringBuilder.append(column);
            stringBuilder.append("' allowed values are: ");
            stringBuilder.append(set);
            stringBuilder.append(". ");

        }
        stringBuilder.append("{responseFormat}");
        return stringBuilder.toString();
    }

    public static String getTodayOutfitPrompt(List<Match> garments, List<String> relevants, LayoutEnum layout) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Make me an outfit from this info: ");
        stringBuilder.append(relevants);
        stringBuilder.append(". I want an outfit made of ");

        if(LayoutEnum.ONE_PIECE.equals(layout)){
            stringBuilder.append("a single one-piece garmet");
        }else{
            stringBuilder.append("at least 1 garment for body, 1 for legs and a cap if I have one in my wardrobe.");
        }

        stringBuilder.append("In my wardrobe I have: ");
        for(Match match : garments){
            stringBuilder.append("a ");
            stringBuilder.append(match.getMainColor());
            stringBuilder.append(" ");
            stringBuilder.append(match.getSubCategory());
            stringBuilder.append(" of ");
            stringBuilder.append(match.getFabric());
            stringBuilder.append(", ");
        }

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
        stringBuilder.append("{locationInfo}");
        stringBuilder.append("{responseFormat}");
        return stringBuilder.toString().replace(", {locationInfo}", ". {locationInfo}");
    }

    public static String getLocationInfo(String location, String date){
        if(StringUtils.isEmpty(location)){
            return  StringUtils.EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder("Make the outfit considering I will be in ");
        stringBuilder.append(location);
        stringBuilder.append(" so consider the weather of this location for ");

        stringBuilder.append(" so consider the weather of this location for ");
        if(StringUtils.isNotEmpty(date)){
            stringBuilder.append("today");
        }else{
            stringBuilder.append(date);
        }

        stringBuilder.append(". ");

        return stringBuilder.toString();
    }
}
