package com.glam.bff.threads;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public class AnalyzeImageThread implements Runnable{

    private CloudVisionTemplate cloudVisionTemplate;

    private Resource resource;

    public AnalyzeImageThread(CloudVisionTemplate cloudVisionTemplate, Resource resource) {
        this.cloudVisionTemplate = cloudVisionTemplate;
        this.resource = resource;
    }

    public volatile AnnotateImageResponse value;
    @Override
    public void run() {
        this.value = cloudVisionTemplate.analyzeImage(
                resource,
                Feature.Type.LABEL_DETECTION,
                Feature.Type.TEXT_DETECTION,
                Feature.Type.IMAGE_PROPERTIES,
                Feature.Type.OBJECT_LOCALIZATION);
    }

    public AnnotateImageResponse getValue() {
        return value;
    }
}
