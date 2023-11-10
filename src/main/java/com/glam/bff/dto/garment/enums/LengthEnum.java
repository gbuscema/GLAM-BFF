package com.glam.bff.dto.garment.enums;

import java.util.List;

/**
 * Used for Dress, Skirt and Jumpsuit
 */
public enum LengthEnum {

  MINI("Mini"),
  MIDI("Midi"),
  LONG("Long");

  private final String value;

  LengthEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static LengthEnum searchMatch(String valueToMatch) {
    var values = List.of(LengthEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    LengthEnum mappedValue = null;
    for (LengthEnum value : values) {
      if (value.value.toLowerCase().equals(normalizedValueToMatch))
        mappedValue = value;
    }
    return mappedValue;
  }
}
