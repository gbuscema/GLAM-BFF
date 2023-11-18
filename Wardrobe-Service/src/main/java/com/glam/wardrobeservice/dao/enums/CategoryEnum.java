package com.glam.wardrobeservice.dao.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CategoryEnum {

  CAPS("Caps"),
  TOPS("Tops"),
  BOTTOM("Bottom"),
  ONE_PIECE("One-Piece"),
  ACCESSORIES("Accessories"),
  SHOES("Shoes");

  private final String value;

  /**
   * Static map to handle the external store redirection
   */
  public static final Map<CategoryEnum, String> externalStoreMap;
  static {
    Map<CategoryEnum, String> aMap = new HashMap<>();
    aMap.put(CategoryEnum.CAPS,
        "https://www.neweracap.eu/it-it/default/copricapo/caps/59fifty-new-york-yankees-mlb-cooperstown-feathered-cord-nero-d04844/");
    aMap.put(CategoryEnum.TOPS,
        "https://www.lacoste.com/it/lacoste/uomo/vestiti/felpe/felpa-jogger-da-uomo-in-cotone-biologico-spazzolato-lacoste/SH9608-00.html?color=CB8&size=10&wiz_source=google&wiz_medium=cpc&wiz_campaign=17865925919&wiz_term&wiz_content&gad_source=1");
    aMap.put(CategoryEnum.BOTTOM,
        "https://www.jeckerson.com/prodotto/pantalone-5-tasche-con-toppa-in-denim/?attribute_pa_colore=blu-medio_214f8d");
    aMap.put(CategoryEnum.ONE_PIECE,
        "https://www.asos.com/it/topman/topman-abito-super-skinny-grigio/grp/125377#colourWayId-202300139");
    aMap.put(CategoryEnum.ACCESSORIES,
        "https://www.gucci.com/it/it/pr/women/accessories-for-women/belts-for-women/leather-belt-with-double-g-buckle-p-409416CVE0T2535");
    aMap.put(CategoryEnum.SHOES, "https://www.nike.com/it/t/scarpa-air-force-1-07-GjGXSP/CW2288-111");
    externalStoreMap = Collections.unmodifiableMap(aMap);
  }

  CategoryEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
