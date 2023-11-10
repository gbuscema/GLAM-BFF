package com.glam.bff.service;

import com.glam.bff.dao.SubcategoryDAO;
import com.glam.bff.dto.garment.enums.SubCategoryEnum;
import com.glam.bff.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import static com.glam.bff.utils.FileUtils.readSvgFile;


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
