package com.glam.wardrobeservice.dao.enums;

import java.util.List;

/**
 * Used for Tshirt, Blouse, Shirt, Top
 */
public enum SleeveLengthEnum {

  SLEEVE_SHORT("Short-Sleeve"),
  SLEEVE_3_4("3/4-Sleeve"),
  SLEEVE_LONG("Long-Sleeve"),
  SHOULDER_STRAPS("Shoulder-Straps"),
  ONE_SHOULDER("One-Shoulder"),
  NO_SLEEVE("No-Sleeve");

  private final String value;

  SleeveLengthEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static SleeveLengthEnum searchMatch(String valueToMatch) {
    var values = List.of(SleeveLengthEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    SleeveLengthEnum mappedValue = null;
    for (SleeveLengthEnum value : values) {
      if (value.value.toLowerCase().equals(normalizedValueToMatch))
        mappedValue = value;
    }
    return mappedValue;
  }
}
