package com.glam.bff.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.apache.commons.beanutils.PropertyUtils;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GarmentWeight extends AbstractGarment{

    public Double getWeight(String column) {
        try{
            Object propValue = PropertyUtils.getProperty(this, column);
            return Double.valueOf(String.valueOf(propValue));
        } catch (Exception e) {
            return 0.5;
        }
    }

}
