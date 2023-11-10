package com.glam.bff.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.glam.bff.dto.garment.enums.CategoryEnum;
import com.glam.bff.dto.garment.enums.SubCategoryEnum;
import org.springframework.stereotype.Component;

import com.glam.bff.dao.GarmentDAO;
import com.glam.bff.model.Match;

@Component
public class DAORelevantDataUtils {

    public <T> List<String> getRelevantData(Class<T> daoClass) {

        if (GarmentDAO.class.equals(daoClass)) {
            Field[] fields = Match.class.getFields();
            return Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public static String getSvgId(SubCategoryEnum subCategoryEnum){
        return subCategoryEnum.getCategory().getValue() + "_" + subCategoryEnum.getValue();
    }

//    public <T> Map<String, Object> getMetadata(Class<T> daoClass){
//        if (GarmentDAO.class.equals(daoClass)) {
//            Map<String, Object> result = new HashMap<>();
//            result("garmentId", )
//            return Arrays.asList("mainColor", "category", "subCategory", "fabric");
//        }
//        return Collections.emptyMap();
//    }
}
