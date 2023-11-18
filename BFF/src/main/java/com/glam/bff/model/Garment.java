package com.glam.bff.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import weka.core.Instance;

import java.lang.reflect.Field;
import java.util.ArrayList;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Garment extends AbstractGarment{

    public static void fromInstance (Instance instance, Object object) throws IllegalAccessException {

        String instanceString = instance.toString();
        String[] values = instanceString.split(",");
        Field[] fields = object.getClass().getDeclaredFields();

        if(values.length != fields.length)
            return;

        for (int i = 0; i < values.length; i++) {
            Field field = fields[i];
            field.set(object, values[i]);
        }

    }
}
