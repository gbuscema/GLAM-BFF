package com.glam.bff.dto.garment.enums;

import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;

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
    var stringComparer = new LevenshteinDistance();
    var values = List.of(FabricEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    FabricEnum mappedValue = null;
    var minDistance = Integer.MAX_VALUE;
    for (FabricEnum value : values) {
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
