package com.glam.bff.dto.garment.enums;

import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;

public enum PatternDetailEnum {

  ORIZONTAL_STRIPES("Orizontal-Stripes"),
  VERTICAL_STRIPES("Vertical-Stripes"),
  OBLIQUE_STRIPES("Oblique-Stripes"),
  BIG_DOTS("Big-Dots"),
  SMALL_DOTS("Small-Dots"),
  HAWAIIAN("Hawaiian"),
  LEOPARD_PRINT("Leopard-Print"),
  ZEBRA_PRINT("Zebra-Print"),
  PYTHON_PRINT("Python-Print");

  private final String value;

  PatternDetailEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static PatternDetailEnum searchMatch(String valueToMatch) {
    var stringComparer = new LevenshteinDistance();
    var values = List.of(PatternDetailEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    PatternDetailEnum mappedValue = null;
    var minDistance = Integer.MAX_VALUE;
    for (PatternDetailEnum value : values) {
      var distance = stringComparer.apply(value.value.toLowerCase(), normalizedValueToMatch);

      if (distance < minDistance) {
        minDistance = distance;
        mappedValue = value;
      }
      // if (value.value.toLowerCase().contains(normalizedValueToMatch))
      // mappedValue = value;
    }
    return minDistance < 2 ? mappedValue : null;
  }
}
