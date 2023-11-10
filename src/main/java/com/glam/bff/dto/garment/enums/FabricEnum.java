package com.glam.bff.dto.garment.enums;

import java.util.List;

/**
 * Used for main and second color
 */
public enum FabricEnum {

  COTTON("Cotton"),
  POLYESTER("Polyester"),
  SILK("Silk"),
  WOOL("Wool"),
  LINEN("Linen"),
  DENIM("Denim"),
  VELVET("Velvet"),
  SATIN("Satin"),
  CHIFFON("Chiffon"),
  LEATHER("Leather"),
  JERSEY("Jersey"),
  SPANDEX("Spandex"),
  TWEED("Tweed"),
  CASHMERE("Cashmere"),
  LYCRA("Lycra"),
  MICROFIBER("Microfiber");

  private final String value;

  FabricEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static FabricEnum searchMatch(String valueToMatch) {
    var values = List.of(FabricEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    FabricEnum mappedValue = null;
    for (FabricEnum value : values) {
      if (value.value.toLowerCase().equals(normalizedValueToMatch))
        mappedValue = value;
    }
    return mappedValue;
  }
}
