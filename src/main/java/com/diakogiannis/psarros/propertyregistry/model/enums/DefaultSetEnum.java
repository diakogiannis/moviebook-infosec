package com.diakogiannis.psarros.propertyregistry.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum DefaultSetEnum {
  ASSET_NOTES(1),
  COUNTRY_STATE(2),
  CURRENCY(3),
  MORTAGE_REGISTRY(4),
  MUNICIPALITY(5),
  MUNICIPALITY_DEPARTMENT(6),
  OWNERSHIP(7),
  OWNERSHIP_TYPE(8),
  PROPERTY_FLOOR(9),
  PROPERTY_STATUS(10),
  PROPERTY_TRANSFER_TYPE(11),
  PROPERTY_TYPE(12);

    private static final Map<Integer, DefaultSetEnum> BY_LABEL = new HashMap<>();

    static {
        for (DefaultSetEnum e : values()) {
            BY_LABEL.put(e.type, e);
        }
    }

  private final Integer type;

  DefaultSetEnum(Integer type) {
    this.type = type;
  }

  public static DefaultSetEnum valueOfIndex(Integer type) {
    return BY_LABEL.get(type);
  }

  public static Integer valueOfType(DefaultSetEnum enumeration) {
    return enumeration.type;
  }

}
