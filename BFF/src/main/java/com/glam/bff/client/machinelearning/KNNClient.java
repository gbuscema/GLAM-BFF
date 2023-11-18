package com.glam.bff.client.machinelearning;


import com.glam.bff.model.GarmentWeight;
import com.glam.bff.model.Match;
import com.glam.bff.model.Garment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import weka.classifiers.lazy.IBk;
import weka.core.*;
import weka.core.converters.CSVLoader;
import weka.core.neighboursearch.*;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

import java.io.File;

@Slf4j
@Service
public class KNNClient {

    public Match getNearestOutfit(Garment garment, GarmentWeight garmentWeight, File wardrobeFile){

        try {

            if(garment == null)
                return null;

            // Load the wardrobe for the specific user (mocked)
            // 1. convert wardrobe list into CSV
            CSVLoader loader = new CSVLoader();
            loader.setSource(wardrobeFile);
            Instances wardrobe = loader.getDataSet();

            // Set class index attribute (complete outfit)
            wardrobe.setClassIndex(wardrobe.numAttributes() - 1);

            // Input Instance Creation -> Attributes
            String[] inputVectorTable = garment.propertyValueList();
            Instance inputOutfit = new DenseInstance(inputVectorTable.length);
            inputOutfit.setDataset(wardrobe);

            // Set outfit's input values
            for(int i = 0; i < inputVectorTable.length; i++) {
                try{
                    inputOutfit.setValue(i, inputVectorTable[i]);
                    Double weight = garmentWeight.getWeight(inputVectorTable[i]);
                    inputOutfit.attribute(i).setWeight(weight);
                } catch (Exception e) {
                    // Log Error
                    log.error(e.getMessage(), e);
                }
            }

            // If necessary, set StringToNominal filter
            StringToNominal stringToNominalFilter = new StringToNominal();
            stringToNominalFilter.setInputFormat(wardrobe);
            wardrobe = Filter.useFilter(wardrobe, stringToNominalFilter);

            // Configure KNN Algorithm
            IBk knn = new IBk();
            NearestNeighbourSearch search = new LinearNNSearch();
            knn.setNearestNeighbourSearchAlgorithm(search);

            // Train KNN Algorithm over wardrobe
            knn.buildClassifier(wardrobe);

            // Find the nearest
            Instance nearestOutfit = search.nearestNeighbour(inputOutfit);
            Match match = new Match();
            Match.fromInstance(nearestOutfit, match);

            return match;

        } catch (Exception e) {
            return null;
        }

    }

}
