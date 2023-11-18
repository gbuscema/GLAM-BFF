package com.glam.wardrobeservice.dao.enums;

import java.util.List;

/**
 * Used for main and second color
 */
public enum SeasonEnum {

  SPRING("Spring"),
  SUMMER("Summer"),
  FALL("Fall"),
  WINTER("Winter"),
  DONT_KNOW("I-Dont-Know"),
  ALL("All");

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
