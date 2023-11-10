package com.glam.bff.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wardrobe {

    @JsonProperty("garments")
    private List<Garment> garments;

    public Map<String, Set<String>> convertWardrobeIntoMap(){

        HashMap<String, Set<String>> map = new HashMap<>();

        // Extract columns
        String[] columns = Garment.propertyList();

        for(String column: columns) {

            // For each column, extract the set
            Set<String> columnSet = this.getGarments().stream().map(garment -> {
                try{
                    Object propValue = PropertyUtils.getProperty(garment, column);
                    return String.valueOf(propValue);
                } catch (Exception e) {
                    return null;
                }
            }).collect(Collectors.toSet());

            // fill the map
            map.put(column, columnSet);

        }

        return map;

    }

}
