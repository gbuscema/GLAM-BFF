package com.glam.bff.dto.garment.enums;

import java.util.List;

public enum SizeEnum {

  XXS("S"),
  XS("XS"),
  S("S"),
  M("M"),
  L("L"),
  XL("XL"),
  XXL("XXL"),
  XXXL("XXXL");

  private final String value;

  SizeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static SizeEnum searchMatch(String valueToMatch) {
    var values = List.of(SizeEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    SizeEnum mappedValue = null;
    for (SizeEnum value : values) {
      if (value.value.toLowerCase().equals(normalizedValueToMatch))
        mappedValue = value;
    }
    return mappedValue;
  }
}
