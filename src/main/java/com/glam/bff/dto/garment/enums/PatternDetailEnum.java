package com.glam.bff.dto.garment.enums;

import java.util.List;

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
    var values = List.of(PatternDetailEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    PatternDetailEnum mappedValue = null;
    for (PatternDetailEnum value : values) {
      if (value.value.toLowerCase().equals(normalizedValueToMatch))
        mappedValue = value;
    }
    return mappedValue;
  }
}
