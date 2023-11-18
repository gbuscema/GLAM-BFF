package com.glam.bff.dto.garment.enums;

import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * Used for main and second color
 */
public enum PatternEnum {

  STRIPES("Stripes"),
  DOTS("Dots"),
  FLORAL("Floral"),
  GEOMETRIC("Geometric"),
  ANIMAL_PRINT("Animal-Print"),
  SOLID_COLOR("Solid-Color");

  private final String value;

  PatternEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static PatternEnum searchMatch(String valueToMatch) {
    var stringComparer = new LevenshteinDistance();
    var values = List.of(PatternEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    PatternEnum mappedValue = null;
    var minDistance = Integer.MAX_VALUE;
    for (PatternEnum value : values) {
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
