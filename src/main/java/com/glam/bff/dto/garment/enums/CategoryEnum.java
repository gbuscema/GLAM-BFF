package com.glam.bff.dto.garment.enums;

import java.util.List;

public enum CategoryEnum {

  CAPS("Caps"),
  TOPS("Tops"),
  BOTTOM("Bottom"),
  ONE_PIECE("One-Piece"),
  ACCESSORIES("Accessories"),
  SHOES("Shoes");

  private final String value;

  CategoryEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static CategoryEnum searchMatch(String valueToMatch) {
    var values = List.of(CategoryEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    CategoryEnum mappedValue = null;
    for (CategoryEnum value : values) {
      if (value.value.toLowerCase().contains(normalizedValueToMatch))
        mappedValue = value;
    }
    return mappedValue;
  }
}
