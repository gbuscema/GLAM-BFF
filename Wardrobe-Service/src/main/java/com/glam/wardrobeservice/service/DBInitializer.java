package com.glam.wardrobeservice.service;

import com.glam.wardrobeservice.dao.SubcategoryDAO;
import com.glam.wardrobeservice.dao.enums.SubCategoryEnum;
import com.glam.wardrobeservice.repository.SubcategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import static com.glam.wardrobeservice.utils.FileUtils.readSvgFile;


@Service
public class DBInitializer {

  public static Logger LOGGER = Logger.getLogger(DBInitializer.class.getName());

  @Autowired
  private SubcategoryRepository subcategoryRepository;

  @PostConstruct
  public void initDB() {
    System.out.println("DBInitializer.initDB");
    Arrays.stream(SubCategoryEnum.values()).filter(subCategoryEnum -> subcategoryRepository.existsById(subCategoryEnum.getCategory().name() + "-" + subCategoryEnum.name()))
            .forEach(subCategoryEnum -> {
              SubcategoryDAO subcategoryDAO = new SubcategoryDAO();
              subcategoryDAO.setSubcategoryId(subCategoryEnum.getCategory().name() + "-" + subCategoryEnum.name());
              try {
                String svgContent = readSvgFile(String.format("src/main/resources/svgs/%s.svg", subcategoryDAO.getSubcategoryId()));
                subcategoryDAO.setSvg(svgContent);
                subcategoryRepository.save(subcategoryDAO);
              } catch (IOException e) {
                LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
              }
            });
  }

}
