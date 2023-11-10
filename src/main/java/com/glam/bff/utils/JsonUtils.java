package com.glam.bff.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glam.bff.model.Garment;
import com.glam.bff.model.Outfit;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static List<Garment> convertJsonIntoMatchList(String jsonStringArray) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        List<Garment> garments;
        garments = Arrays.asList(mapper.readValue(jsonStringArray, Garment[].class));

        return garments;

    }

    public static Outfit convertJsonIntoOutfit(String jsonString) {

        try{

            ObjectMapper mapper = new ObjectMapper();
            Outfit outfit;
            outfit = mapper.readValue(jsonString, Outfit.class);

            return outfit;

        } catch (Exception e) {
            return new Outfit();
        }

    }

    public static Object convertJsonFileIntoObject(InputStream is, Class objectClass) {

        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(is, objectClass);

        } catch (Exception e) {
            return null;
        }

    }

}
