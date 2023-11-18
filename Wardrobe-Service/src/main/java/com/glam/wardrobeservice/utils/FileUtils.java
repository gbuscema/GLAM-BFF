package com.glam.wardrobeservice.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    public static File writeToFile(String pFilename, String fileString) throws IOException {
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File tempFile = File.createTempFile(pFilename, ".tmp", tempDir);
        FileWriter fileWriter = new FileWriter(tempFile, true);
        System.out.println(tempFile.getAbsolutePath());
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write(fileString);
        bw.close();
        return tempFile;
    }

    public static String readSvgFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
