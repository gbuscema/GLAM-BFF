package com.glam.bff.dto.garment.enums;

import java.util.List;

/**
 * Used for main and second color
 */
public enum ColorEnum {

  BLACK("Black", 0, 0, 0),
  WHITE("White", 255, 255, 255),
  GRAY("Gray", 80, 80, 80),
  BLUE("Blue", 0, 0, 255),
  LIGHT_BLUE("Light-Blue", 173, 216, 230),
  RED("Red", 255, 0, 0),
  GREEN("Green", 0, 255, 0),
  DARK_SEA_GREEN("Dark-Sea-Green", 33, 94, 79),
  DARK_GREEN("Dark-Green", 3, 82, 24),
  OLIVE_GREEN("Olive-Green", 112, 130, 56),
  YELLOW("Yellow", 255, 255, 0),
  BROWN("Brown", 66, 40, 14),
  PINK("Pink", 255, 192, 203),
  PURPLE("Purple", 128, 0, 128),
  ORANGE("Orange", 255, 165, 0),
  BEIGE("Beige", 232, 220, 202),
  SILVER("Silver", 192, 192, 192),
  GOLD("Gold", 212, 175, 55),
  MULTICOLOR("Multicolor");

  private final String value;
  private int r;
  private int g;
  private int b;

  ColorEnum(String value) {
    this.value = value;
  }

  ColorEnum(String value, int r, int g, int b) {
    this.value = value;
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public String getValue() {
    return value;
  }

  public int computeMSE(int pixR, int pixG, int pixB) {
    return (int) (((pixR - r) * (pixR - r) + (pixG - g) * (pixG - g) + (pixB - b)
        * (pixB - b)) / 3);
  }

  public String toString() {
    return String.format("#%02x%02x%02x", this.r, this.g, this.b);
  }

  public static ColorEnum searchMatch(float pixR, float pixG, float pixB) {
    var colors = List.of(ColorEnum.values());
    var intR = Math.round(pixR);
    var intG = Math.round(pixG);
    var intB = Math.round(pixB);
    ColorEnum closestMatch = null;
    int minMSE = Integer.MAX_VALUE;
    int mse;
    for (ColorEnum c : colors) {
      mse = c.computeMSE(intR, intG, intB);
      if (mse < minMSE) {
        minMSE = mse;
        closestMatch = c;
      }
    }

    if (closestMatch != null) {
      return closestMatch;
    }
    return ColorEnum.MULTICOLOR;
  }
}
