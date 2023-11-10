package com.glam.bff.utils;

import com.glam.bff.model.Garment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    public static ArrayList<String[]> convertCSVStringIntoArrayList(String csvString) {

        String[] rows = csvString.split("/n");
        ArrayList<String[]> vectorTableList = new ArrayList<>();

        for(int i = 1; i < rows.length; i++) {

            String row = rows[i];
            String[] matchVectorTable = row.split(",");
            vectorTableList.add(matchVectorTable);

        }

        return vectorTableList;

    }

    public static String convertGarmentListIntoCSV(List<Garment> list) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();

        for (Field f : Garment.class.getSuperclass().getDeclaredFields()) {
            builder.append(f.getName());
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("\n");
        list.forEach(row -> builder.append(row.toString()));

        return builder.toString();
    }

}
