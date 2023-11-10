package com.glam.bff.dto.garment.enums;

import java.util.List;

/**
 * Used for main and second color
 */
public enum SeasonEnum {

  SPRING("Spring"),
  SUMMER("Summer"),
  FALL("Fall"),
  WINTER("Winter");

  private final String value;

  SeasonEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static SeasonEnum searchMatch(String valueToMatch) {
    var values = List.of(SeasonEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    SeasonEnum mappedValue = null;
    for (SeasonEnum value : values) {
      if (value.value.toLowerCase().equals(normalizedValueToMatch))
        mappedValue = value;
    }
    return mappedValue;
  }
}
