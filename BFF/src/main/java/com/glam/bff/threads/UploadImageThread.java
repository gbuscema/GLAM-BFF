package com.glam.bff.threads;

import com.glam.bff.service.WardrobeService;
import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.io.File;

public class UploadImageThread implements Runnable{

    private WardrobeService wardrobeService;

    public UploadImageThread(WardrobeService wardrobeService){
        this.wardrobeService = wardrobeService;
    }

    public volatile String textureUrl;
    @Override
    public void run() {

        this.textureUrl = wardrobeService.uploadImage(new File("out-temp.png"));
    }

    public String getTextureUrl(){
        return this.textureUrl;
    }

}
