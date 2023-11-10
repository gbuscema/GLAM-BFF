package com.glam.bff.dto.garment.enums;

import java.util.List;

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
    var values = List.of(PatternEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    PatternEnum mappedValue = null;
    for (PatternEnum value : values) {
      if (value.value.toLowerCase().equals(normalizedValueToMatch))
        mappedValue = value;
    }
    return mappedValue;
  }
}
