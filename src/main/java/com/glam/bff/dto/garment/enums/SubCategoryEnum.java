package com.glam.bff.dto.garment.enums;

import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.netlib.util.floatW;

public enum SubCategoryEnum {

  // CAPS
  BASEBALL_CAPS("Baseball-Caps", CategoryEnum.CAPS),
  BEANIE_WOLLY("Beanie-Wolly", CategoryEnum.CAPS),
  WIDE_BRIMMED_HAT("WIDE-Brimmed-Hat", CategoryEnum.CAPS),
  FEDORA("Fedora", CategoryEnum.CAPS),
  PEAKY_BLINDERS("Peaky-Blinders", CategoryEnum.CAPS),
  HATS("Hats", CategoryEnum.CAPS),

  // TOPS
  T_SHIRT("T-Shirt", CategoryEnum.TOPS),
  BLOUSE("Blouse", CategoryEnum.TOPS),
  SHIRT("Shirt", CategoryEnum.TOPS),
  TOP("Top", CategoryEnum.TOPS),
  POLO("Polo", CategoryEnum.TOPS),
  TUNIC("Tunic", CategoryEnum.TOPS),
  SWEATER("Sweater", CategoryEnum.TOPS),
  JACKET("Jacket", CategoryEnum.TOPS),

  // BOTTOMS
  JEANS("Jeans", CategoryEnum.BOTTOM),
  LEGGINGS("Leggings", CategoryEnum.BOTTOM),
  SHORTS("Shorts", CategoryEnum.BOTTOM),
  TROUSERS("Trousers", CategoryEnum.BOTTOM),
  TRACKSUIT("Tracksuit", CategoryEnum.BOTTOM),
  SKIRT("Skirt", CategoryEnum.BOTTOM),

  // ONE PIECE
  DRESS("Dress", CategoryEnum.ONE_PIECE),
  SUIT("Suit", CategoryEnum.ONE_PIECE),
  JUMPSUIT("Jumpsuit", CategoryEnum.ONE_PIECE),

  // SHOES
  SNEAKERS("Sneakers", CategoryEnum.SHOES),
  BOOTS("Boots", CategoryEnum.SHOES),
  HIGH_HEELS("High-Heels", CategoryEnum.SHOES),
  HIGH_WEEL("High-Weel", CategoryEnum.SHOES),
  SANDALS("Sandals", CategoryEnum.SHOES),
  LOAFERS("Loafers", CategoryEnum.SHOES),
  WEDGES("Wedges", CategoryEnum.SHOES),

  // ACCESSORIES
  BELT("Belt", CategoryEnum.ACCESSORIES),
  ACCESSORIES("Accessories", CategoryEnum.ACCESSORIES),
  SCARVES("Scarves", CategoryEnum.ACCESSORIES),
  JEWELRY("Jewelry", CategoryEnum.ACCESSORIES);

  private final String value;
  private final CategoryEnum category;

  SubCategoryEnum(String value, CategoryEnum category) {
    this.value = value;
    this.category = category;
  }

  public String getValue() {
    return value;
  }

  public CategoryEnum getCategory() {
    return category;
  }

  public static SubCategoryEnum searchMatch(String valueToMatch) {
    var stringComparer = new LevenshteinDistance();
    var values = List.of(SubCategoryEnum.values());
    var normalizedValueToMatch = valueToMatch.toLowerCase();
    SubCategoryEnum mappedValue = null;
    var minDistance = Float.MAX_VALUE;
    for (SubCategoryEnum value : values) {
      var distance = stringComparer.apply(value.value.toLowerCase(), normalizedValueToMatch);

      if (distance < minDistance) {
        minDistance = distance;
        mappedValue = value;
      }
      // if (value.value.toLowerCase().contains(normalizedValueToMatch))
      // mappedValue = value;
    }
    System.out.println(valueToMatch);
    System.out.println(minDistance);
    System.out.println(mappedValue.value);
    return minDistance < 2 ? mappedValue : null;
  }
}
