package com.glam.bff.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import weka.core.Instance;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractGarment{

    @JsonProperty("description")
    public String description;

    @JsonProperty("mainColor")
    public String mainColor;

    @JsonProperty("category")
    public String category;

    @JsonProperty("subCategory")
    public String subCategory;

    @JsonProperty("fabric")
    public String fabric;
    
    public static String[] propertyList() {
        ArrayList<String> propertyList = new ArrayList<>();
        for (Field f : AbstractGarment.class.getDeclaredFields()) {
            propertyList.add(f.getName());
        }
        return propertyList.toArray(new String[propertyList.size()]);
    }

    public String[] propertyValueList() {
        ArrayList<String> propertyList = new ArrayList<>();
        for (Field f : this.getClass().getSuperclass().getDeclaredFields()) {
            try{
                propertyList.add(String.valueOf(f.get(this)));
            } catch (Exception e) {
                propertyList.add("");
            }
        }
        return propertyList.toArray(new String[propertyList.size()]);
    }

//    public String toString(){
//        StringBuilder builder = new StringBuilder();
//
//        // Append values
//        for (Field f : this.getClass().getSuperclass().getDeclaredFields()) {
//
//            try {
//                builder.append(f.get(this));
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//            builder.append(",");
//
//        }
//
//        builder.deleteCharAt(builder.length() - 1);
//        builder.append("\n");
//
//        return builder.toString();
//    }

}
